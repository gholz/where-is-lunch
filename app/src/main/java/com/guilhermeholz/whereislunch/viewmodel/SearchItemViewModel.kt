package com.guilhermeholz.whereislunch.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableFloat
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.ui.navigation.RestaurantNavigator

class SearchItemViewModel(val navigator: RestaurantNavigator) : BaseObservable() {

    val title: ObservableField<String> = ObservableField()
    val thumb: ObservableField<String> = ObservableField()
    val votes: ObservableField<String> = ObservableField()
    val rating: ObservableFloat = ObservableFloat()

    private var restaurantId: String? = null

    fun bind(restaurant: Restaurant) {
        title.set(restaurant.name)
        thumb.set(restaurant.picture)
        rating.set(restaurant.rating)
        votes.set(restaurant.votes.toString())
        restaurantId = restaurant.id
    }

    fun onClickItem() {
        restaurantId?.let { navigator.navigateToDetail(it) }
    }
}