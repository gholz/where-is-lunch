package com.guilhermeholz.whereislunch.viewmodel

import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.ui.navigation.RestaurantNavigator
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchItemViewModelTest {

    @Mock
    lateinit var navigator: RestaurantNavigator
    lateinit var viewModel: SearchItemViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchItemViewModel(navigator)
    }

    @Test
    fun shouldBindCorrectly() {
        val restaurant = getRestaurant()
        viewModel.bind(restaurant)
        assert(viewModel.title.get() == restaurant.name)
        assert(viewModel.thumb.get() == restaurant.picture)
        assert(viewModel.rating.get() == restaurant.rating)
        assert(viewModel.votes.get() == restaurant.votes.toString())
    }

    @Test
    fun shouldOpenDetailScreenOnClick() {
        viewModel.bind(getRestaurant())
        viewModel.onClickItem()
        verify(navigator).navigateToDetail(any())
    }

    fun getRestaurant(): Restaurant {
        return Restaurant("id", "name", "picture", 4.5f, 4)
    }
}