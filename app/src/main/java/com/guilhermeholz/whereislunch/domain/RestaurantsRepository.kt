package com.guilhermeholz.whereislunch.domain

import android.content.SharedPreferences
import android.location.Location
import android.support.annotation.VisibleForTesting
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.utils.DateManager
import com.guilhermeholz.whereislunch.utils.OpenForTesting
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import rx.Observable

@OpenForTesting
class RestaurantsRepository(val dataSource: RestaurantsDataSource,
                            val dateManager: DateManager,
                            val preferences: SharedPreferences) {

    fun getRestaurants(location: Location): Observable<List<Restaurant>> {
        return dataSource.getRestaurants(location, dateManager.getCurrentDate())
    }

    fun getRestaurant(id: String): Observable<RestaurantDetail> {
        return dataSource.getRestaurant(id, dateManager.getCurrentDate())
    }

    fun getWinner(): Observable<RestaurantDetail> {
        return dataSource.getWinner(dateManager.getCurrentDate())
    }

    fun vote(restaurant: RestaurantDetail): Observable<RestaurantDetail> {
        val currentDate = dateManager.getCurrentDate()
        preferences.edit().putBoolean(restaurant.id + currentDate, true).apply()
        return dataSource.vote(restaurant.id, currentDate)
    }

    fun canVote(id: String): Boolean = !isVotingClosed() && !preferences.contains(id + dateManager.getCurrentDate())

    fun isVotingClosed(): Boolean = dateManager.isAfterMidDay()
}