package com.guilhermeholz.whereislunch.domain.datasource

import android.location.Location
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import rx.Observable

interface RestaurantsDataSource {
    fun getRestaurants(location: Location): Observable<List<Restaurant>>
    fun getRestaurant(id: String): Observable<RestaurantDetail>
}