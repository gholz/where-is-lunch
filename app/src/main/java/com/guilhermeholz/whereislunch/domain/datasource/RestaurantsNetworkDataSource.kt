package com.guilhermeholz.whereislunch.domain.datasource

import android.location.Location
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.model.SearchResponse
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by fenrir on 26/12/16.
 */
class RestaurantsNetworkDataSource(val api: YelpApi) : RestaurantsDataSource {
    override fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return api.search(location.latitude, location.longitude)
                .subscribeOn(Schedulers.io())
                .map(SearchResponse::businesses)
                .flatMap { Observable.from(it) }
                .map { Restaurant(it.name, it.imageUrl, it.rating, 0) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
    }
}