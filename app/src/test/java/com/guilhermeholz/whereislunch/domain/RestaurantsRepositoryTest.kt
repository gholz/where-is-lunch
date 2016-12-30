package com.guilhermeholz.whereislunch.domain

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.utils.DateManager
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.fakes.RoboSharedPreferences
import rx.Observable

class RestaurantsRepositoryTest {

    @Mock lateinit var dataSource: RestaurantsDataSource
    @Mock lateinit var dateManager: DateManager
    lateinit var preferences: SharedPreferences
    lateinit var repository: RestaurantsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        preferences = RoboSharedPreferences(mutableMapOf(), "content", Context.MODE_PRIVATE)
        repository = RestaurantsRepository(dataSource, dateManager, preferences)
    }

    @Test
    fun shouldRouteGetRestaurants() {
        val location = Location("")
        val expectedResult = Observable.just<List<Restaurant>>(listOf())

        whenever(dataSource.getRestaurants(location, ""))
                .thenReturn(expectedResult)
        whenever(dateManager.getCurrentDate())
                .thenReturn("")

        val result = repository.getRestaurants(location)
        verify(dataSource).getRestaurants(location, "")
        assert(result == expectedResult)
    }

    @Test
    fun shouldRouteGetRestaurant() {
        val expectedResult = Observable.just<RestaurantDetail>(mock())

        whenever(dataSource.getRestaurant("", "")).thenReturn(expectedResult)
        whenever(dateManager.getCurrentDate()).thenReturn("")

        val result = repository.getRestaurant("")
        verify(dataSource).getRestaurant("", "")
        assert(result == expectedResult)
    }

    @Test
    fun shouldRouteGetWinner() {
        val expectedResult = Observable.just<RestaurantDetail>(mock())

        whenever(dataSource.getWinner("")).thenReturn(expectedResult)
        whenever(dateManager.getCurrentDate()).thenReturn("")

        val result = repository.getWinner()
        verify(dataSource).getWinner("")
        assert(result == expectedResult)
    }

    @Test
    fun shouldRouteVote() {
        val detail = RestaurantDetail("", "", "", "", 0f, "", 0)
        val expectedResult = Observable.just<RestaurantDetail>(mock())

        whenever(dataSource.vote("", "")).thenReturn(expectedResult)
        whenever(dateManager.getCurrentDate()).thenReturn("")

        val result = repository.vote(detail)
        verify(dataSource).vote("", "")
        assert(result == expectedResult)
    }

    @Test
    fun shouldReturnClosedAfterMidDay() {
        whenever(dateManager.isAfterMidDay()).thenReturn(true)
        assert(repository.isVotingClosed())
    }

    @Test
    fun shouldReturnOpenBeforeMidDay() {
        whenever(dateManager.isAfterMidDay()).thenReturn(false)
        assert(!repository.isVotingClosed())
    }

    @Test
    fun canVoteShouldReturnFalseAfterVote() {
        val detail = RestaurantDetail("id", "", "", "", 0f, "", 0)
        val expectedResult = Observable.just<RestaurantDetail>(mock())

        whenever(dataSource.vote("id", "00_00_0000")).thenReturn(expectedResult)
        whenever(dateManager.getCurrentDate()).thenReturn("00_00_0000")
        whenever(dateManager.isAfterMidDay()).thenReturn(false)

        repository.vote(detail)

        assert(!repository.canVote("id"))
    }
}