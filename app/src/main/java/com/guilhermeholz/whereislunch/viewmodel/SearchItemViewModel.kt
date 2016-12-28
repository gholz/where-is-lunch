package com.guilhermeholz.whereislunch.viewmodel

import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableFloat
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.ui.activities.RestaurantDetailActivity

class SearchItemViewModel(val context: Context) : BaseObservable() {

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
        restaurantId?.let {
            val intent = Intent(context, RestaurantDetailActivity::class.java)
                    .putExtra(RestaurantDetailActivity.idExtraKey, it)
            context.startActivity(intent)
        }
    }
}