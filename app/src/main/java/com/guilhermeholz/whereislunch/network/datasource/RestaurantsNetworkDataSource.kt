package com.guilhermeholz.whereislunch.network.datasource

import android.content.SharedPreferences
import android.location.Location
import com.guilhermeholz.whereislunch.BuildConfig
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.model.AuthResponse
import com.guilhermeholz.whereislunch.network.model.SearchResponse
import rx.Observable
import java.util.*

class RestaurantsNetworkDataSource(val api: YelpApi, val preferences: SharedPreferences) : RestaurantsDataSource {

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
        return api.getBusinessDetail(authHeader, id).map {
            RestaurantDetail(it.name, it.phone, it.imageUrl, it.rating,
                    "${it.location.address1}, ${it.location.city} ${it.location.country}", 0)
        }
    }

    private fun authenticate() = api.getAuthToken("client_credentials",
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
        return api.search(authHeader, location.latitude, location.longitude)
                .map(SearchResponse::businesses)
                .flatMap { Observable.from(it) }
                .map { Restaurant(it.id, it.name, it.imageUrl, it.rating, 0) }
                .toSortedList { x, y -> y.votes - x.votes }
    }
}