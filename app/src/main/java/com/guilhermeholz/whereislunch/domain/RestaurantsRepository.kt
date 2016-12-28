package com.guilhermeholz.whereislunch.domain

import android.location.Location
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import rx.Observable
import java.util.concurrent.TimeUnit

class RestaurantsRepository(val dataSource: RestaurantsDataSource) {
    fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return dataSource.getRestaurants(location)
    }

    fun getRestaurant(id: String): Observable<RestaurantDetail> {
        return dataSource.getRestaurant(id)
    }

    fun vote(restaurant: RestaurantDetail): Observable<RestaurantDetail> {
        return Observable.just(RestaurantDetail(restaurant.name, restaurant.phone, restaurant.image,
                restaurant.rating, restaurant.address, restaurant.votes + 1))
                .delay(5, TimeUnit.SECONDS)
    }
}