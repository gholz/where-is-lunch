package com.guilhermeholz.whereislunch.domain

import android.content.SharedPreferences
import android.location.Location
import android.support.annotation.VisibleForTesting
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.utils.OpenForTesting
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import rx.Observable

@OpenForTesting
class RestaurantsRepository(val dataSource: RestaurantsDataSource, val preferences: SharedPreferences) {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy")

    fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return dataSource.getRestaurants(location, getCurrentDate())
    }

    fun getRestaurant(id: String): Observable<RestaurantDetail> {
        return dataSource.getRestaurant(id, getCurrentDate())
    }

    fun getWinner(): Observable<RestaurantDetail> {
        return dataSource.getWinner(getCurrentDate())
    }

    fun vote(restaurant: RestaurantDetail): Observable<RestaurantDetail> {
        val currentDate = getCurrentDate()
        preferences.edit().putBoolean(restaurant.id + currentDate, true).apply()
        return dataSource.vote(restaurant.id, currentDate)
    }

    fun canVote(id: String): Boolean = !isVotingClosed() && !preferences.contains(id + getCurrentDate())

    fun isVotingClosed(): Boolean = LocalTime.now().isAfter(LocalTime.NOON.plusMinutes(30))

    private fun getCurrentDate(): String = formatter.format(LocalDate.now())
}