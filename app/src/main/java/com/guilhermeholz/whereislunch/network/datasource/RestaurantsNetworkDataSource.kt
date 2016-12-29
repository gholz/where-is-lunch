package com.guilhermeholz.whereislunch.network.datasource

import android.content.SharedPreferences
import android.location.Location
import com.guilhermeholz.whereislunch.BuildConfig
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.network.VotingApi
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.model.votes.Vote
import com.guilhermeholz.whereislunch.network.model.votes.VotesResponse
import com.guilhermeholz.whereislunch.network.model.yelp.AuthResponse
import com.guilhermeholz.whereislunch.network.model.yelp.BusinessDetail
import com.guilhermeholz.whereislunch.network.model.yelp.SearchResponse
import rx.Observable
import java.util.*

class RestaurantsNetworkDataSource(val yelpApi: YelpApi, val votingApi: VotingApi, val preferences: SharedPreferences) : RestaurantsDataSource {

    private var authHeader = ""
    private var expiration = 0L
    private val authHeaderKey = "yelp_authorization_header"
    private val tokenExpirationKey = "yelp_token_expiration"

    init {
        expiration = preferences.getLong(tokenExpirationKey, 0)
        authHeader = preferences.getString(authHeaderKey, "")
    }

    override fun getRestaurants(location: Location, date: String): Observable<List<Restaurant>> {
        return if (!isAuthenticated()) {
            authenticate().flatMap {
                store(it)
                searchByLocation(location, date)
            }
        } else {
            searchByLocation(location, date)
        }
    }

    override fun getRestaurant(id: String, date: String): Observable<RestaurantDetail> {
        return if (!isAuthenticated()) {
            authenticate().flatMap {
                store(it)
                getRestaurantById(id, date)
            }
        } else {
            getRestaurantById(id, date)
        }
    }

    override fun vote(id: String, date: String): Observable<RestaurantDetail> {
        return votingApi.vote(id, date).flatMap { getRestaurant(id, date) }
    }

    private fun isAuthenticated(): Boolean = !authHeader.isEmpty() && expiration < Date().time

    private fun getRestaurantById(id: String, date: String): Observable<RestaurantDetail> {
        return yelpApi.getBusinessDetail(authHeader, id)
                .zipWith(votingApi.getVotesById(id, date)) {
                    business, vote ->
                    RestaurantDetail(business.id,
                            business.name,
                            business.phone,
                            business.imageUrl,
                            business.rating,
                            getAddressLine(business),
                            vote.amount)
                }
    }

    private fun authenticate() = yelpApi.getAuthToken("client_credentials",
            BuildConfig.YELP_CLIENT_ID,
            BuildConfig.YELP_SECRET_KEY)

    private fun store(authentication: AuthResponse) {
        authHeader = "Bearer ${authentication.accessToken}"
        preferences.edit()
                .putString(authHeaderKey, authHeader)
                .putLong(tokenExpirationKey, authentication.expiresIn)
                .apply()
    }

    private fun searchByLocation(location: Location, date: String): Observable<List<Restaurant>> {
        return yelpApi.search(authHeader, location.latitude, location.longitude)
                .zipWith(votingApi.getVotes(date), {
                    business, votes ->
                    combine(business, votes).sortedByDescending(Restaurant::votes)
                })
    }

    private fun getAddressLine(business: BusinessDetail) = "${business.location.address1}, ${business.location.city} ${business.location.country}"

    private fun combine(searchResponse: SearchResponse, votesResponse: VotesResponse): List<Restaurant> {
        val votes = votesResponse.votes.associateBy(Vote::id)
        return searchResponse.businesses.map {
            Restaurant(it.id,
                    it.name,
                    it.imageUrl,
                    it.rating,
                    votes[it.id]?.amount ?: 0)
        }
    }
}