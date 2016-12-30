package com.guilhermeholz.whereislunch.viewmodel

import android.content.Context
import android.location.Location
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.utils.RxJavaTestingUtils
import com.guilhermeholz.whereislunch.view.SearchView
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.fakes.RoboSharedPreferences
import rx.Observable

class SearchViewModelTest {

    private val preferences = RoboSharedPreferences(mutableMapOf(), "default", Context.MODE_PRIVATE)

    @Mock
    lateinit var view: SearchView

    @Mock
    lateinit var repository: RestaurantsRepository

    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        RxJavaTestingUtils.setupRxSchedulers()
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(preferences, repository)
        viewModel.view = view
    }

    @Test
    fun shouldDisplayRestaurantOnSuccessfulRequest() {
        val location = mock<Location>()
        whenever(repository.getRestaurants(location))
                .thenReturn(Observable.just(listOf<Restaurant>(mock())))

        viewModel.loadRestaurants(location)
        verify(view).displayRestaurants(any())
    }

    @Test
    fun shouldDisplayEmptyMessageWhitEmptyResponse() {
        val location = mock<Location>()
        whenever(repository.getRestaurants(location))
                .thenReturn(Observable.just(listOf<Restaurant>()))

        viewModel.loadRestaurants(location)
        assert(viewModel.empty.get())
    }

    @Test
    fun shouldNotCallMethodTwiceForSameLocation() {
        val location = mock<Location>()
        whenever(repository.getRestaurants(location))
                .thenReturn(Observable.just(listOf<Restaurant>(mock())))

        viewModel.loadRestaurants(location)
        verify(view).displayRestaurants(any())

        viewModel.loadRestaurants(location)
        verifyNoMoreInteractions(view)
    }

    @After
    fun tearDown() {
        RxJavaTestingUtils.resetSchedulers()
    }
}