package com.guilhermeholz.whereislunch.domain

import android.location.Location
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsNetworkDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import rx.Observable

/**
 * Created by fenrir on 26/12/16.
 */
class RestaurantsRepository(val network: RestaurantsDataSource) {
    fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return network.getRestaurants(location)
    }
}