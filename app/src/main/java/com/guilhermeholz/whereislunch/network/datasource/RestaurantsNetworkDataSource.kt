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

    override fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return if (isAuthenticated()) {
            authenticate().flatMap { searchByLocation(location) }
        } else {
            searchByLocation(location)
        }
    }

    override fun getRestaurant(id: String): Observable<RestaurantDetail> {
        return if (isAuthenticated()) {
            authenticate().flatMap { getRestaurantById(id) }
        } else {
            getRestaurantById(id)
        }
    }

    private fun isAuthenticated(): Boolean = !authHeader.isEmpty() && expiration < Date().time

    private fun getRestaurantById(id: String): Observable<RestaurantDetail> {
        return yelpApi.getBusinessDetail(authHeader, id).map {
            RestaurantDetail(it.name, it.phone, it.imageUrl, it.rating,
                    "${it.location.address1}, ${it.location.city} ${it.location.country}", 0)
        }
    }

    private fun authenticate() = yelpApi.getAuthToken("client_credentials",
            BuildConfig.YELP_CLIENT_ID,
            BuildConfig.YELP_SECRET_KEY)
            .doOnNext { store(it) }

    private fun store(authentication: AuthResponse) {
        authHeader = "Bearer ${authentication.accessToken}"
        preferences.edit()
                .putString(authHeaderKey, authHeader)
                .putLong(tokenExpirationKey, authentication.expiresIn)
                .apply()
    }

    private fun searchByLocation(location: Location): Observable<List<Restaurant>> {
        return yelpApi.search(authHeader, location.latitude, location.longitude)
                .zipWith(votingApi.getVotes(""), { x, y -> combine(x, y) })
    }

    private fun combine(searchResponse: SearchResponse, votesResponse: VotesResponse): List<Restaurant> {
        val votes = votesResponse.votes.associateBy(Vote::id)
        return searchResponse.businesses.map {
            Restaurant(it.id, it.name, it.imageUrl, it.rating, votes[it.id]?.amount ?: 0)
        }.sortedBy(Restaurant::votes)
    }
}