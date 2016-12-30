package com.guilhermeholz.whereislunch.network.datasource

import android.content.Context
import android.content.SharedPreferences
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.network.VotingApi
import com.guilhermeholz.whereislunch.network.VotingApiMockFactory
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.YelpApiMockFactory
import com.guilhermeholz.whereislunch.utils.RxJavaTestingUtils
import com.nhaarman.mockito_kotlin.mock
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.robolectric.fakes.RoboSharedPreferences
import rx.observers.TestSubscriber

class RestaurantsNetworkDataSourceTest {

    lateinit var votingApi: VotingApi
    lateinit var yelpApi: YelpApi
    lateinit var preferences: SharedPreferences
    lateinit var dataSource: RestaurantsNetworkDataSource

    @Before
    fun setUp() {
        RxJavaTestingUtils.setupRxSchedulers()
        yelpApi = YelpApiMockFactory.getApi()
        votingApi = VotingApiMockFactory.getApi()
        preferences = RoboSharedPreferences(mutableMapOf(), "content", Context.MODE_PRIVATE)
        dataSource = RestaurantsNetworkDataSource(yelpApi, votingApi, preferences)
    }

    @Test
    fun shouldReturnRestaurantDetailOnRequest() {
        val subscriber: TestSubscriber<RestaurantDetail> = TestSubscriber()
        dataSource.getRestaurant("", "").subscribe(subscriber)
        subscriber.assertNoErrors()
    }

    @Test
    fun shouldReturnRestaurantsListOnRequest() {
        val subscriber: TestSubscriber<List<Restaurant>> = TestSubscriber()
        dataSource.getRestaurants(mock(), "").subscribe(subscriber)
        subscriber.assertNoErrors()
    }

    @Test
    fun shouldReturnRestaurantDetailOnVoteRequest() {
        val subscriber: TestSubscriber<RestaurantDetail> = TestSubscriber()
        dataSource.vote("", "").subscribe(subscriber)
        subscriber.assertNoErrors()
    }

    @After
    fun tearDown() {
        RxJavaTestingUtils.resetSchedulers()
    }
}