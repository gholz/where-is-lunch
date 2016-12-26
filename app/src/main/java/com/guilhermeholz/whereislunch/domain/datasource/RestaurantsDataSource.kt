package com.guilhermeholz.whereislunch.domain.datasource

import android.location.Location
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import rx.Observable

/**
 * Created by fenrir on 26/12/16.
 */
interface RestaurantsDataSource {
    fun getRestaurants(location:Location): Observable<List<Restaurant>>
}