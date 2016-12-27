package com.guilhermeholz.whereislunch.domain.datasource

import android.content.SharedPreferences
import android.location.Location
import com.guilhermeholz.whereislunch.BuildConfig
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.model.AuthResponse
import com.guilhermeholz.whereislunch.network.model.SearchResponse
import rx.Observable
import java.security.SecureRandom
import java.util.*

class RestaurantsNetworkDataSource(val api: YelpApi, val preferences: SharedPreferences) : RestaurantsDataSource {

    private var authHeader = ""
    private val authHeaderKey = "yelp_authorization_header"
    private val tokenExpirationKey = "yelp_token_expiration"
    private val randomizer = SecureRandom()

    init {
        val expiration = preferences.getLong(tokenExpirationKey, 0)
        if (expiration > Date().time) {
            authHeader = preferences.getString(authHeaderKey, "")
        }
    }

    override fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return if (authHeader.isEmpty()) {
            authenticate().flatMap { searchByLocation(location) }
        } else {
            searchByLocation(location)
        }
    }

    private fun authenticate() = api.getAuthToken("client_credentials",
            BuildConfig.YELP_CLIENT_ID,
            BuildConfig.YELP_SECRET_KEY)
            .doOnNext { store(it) }

    private fun store(authentication: AuthResponse) {
        authHeader = "Bearer ${authentication.accessToken}"
        preferences.edit()
                .putString(authHeaderKey, authentication.accessToken)
                .putLong(tokenExpirationKey, authentication.expiresIn)
                .apply()
    }

    private fun searchByLocation(location: Location): Observable<List<Restaurant>> {
        return api.search(authHeader, location.latitude, location.longitude)
                .map(SearchResponse::businesses)
                .flatMap { Observable.from(it) }
                .map { Restaurant(it.name, it.imageUrl, it.rating, randomizer.nextInt(6)) }
                .toSortedList { x, y -> y.votes - x.votes }
    }
}