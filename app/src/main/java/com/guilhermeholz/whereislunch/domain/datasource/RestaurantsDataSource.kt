package com.guilhermeholz.whereislunch.domain.datasource

import android.location.Location
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.utils.OpenForTesting
import rx.Observable

@OpenForTesting
interface RestaurantsDataSource {
    fun getRestaurants(location: Location, date: String): Observable<List<Restaurant>>
    fun getRestaurant(id: String, date: String): Observable<RestaurantDetail>
    fun getWinner(date: String): Observable<RestaurantDetail>
    fun vote(id: String, date: String): Observable<RestaurantDetail>
}