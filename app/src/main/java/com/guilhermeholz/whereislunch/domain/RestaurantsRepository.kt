package com.guilhermeholz.whereislunch.domain

import android.content.SharedPreferences
import android.location.Location
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import rx.Observable

class RestaurantsRepository(val dataSource: RestaurantsDataSource, val preferences: SharedPreferences) {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy")

    fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return dataSource.getRestaurants(location, getCurrentDate())
    }

    fun getRestaurant(id: String): Observable<RestaurantDetail> {
        return dataSource.getRestaurant(id, getCurrentDate())
    }

    fun vote(restaurant: RestaurantDetail): Observable<RestaurantDetail> {
        val currentDate = getCurrentDate()
        preferences.edit().putBoolean(restaurant.id + currentDate, true).apply()
        return dataSource.vote(restaurant.id, currentDate)
    }

    fun canVote(id: String): Boolean {
        val limit = LocalTime.NOON.plusHours(1)
        return !LocalTime.now().isAfter(limit) && !preferences.contains(id + getCurrentDate())
    }

    private fun getCurrentDate() = formatter.format(LocalDate.now())
}