package com.guilhermeholz.whereislunch.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableFloat
import com.guilhermeholz.whereislunch.domain.model.Restaurant

class SearchItemViewModel : BaseObservable() {

    val title: ObservableField<String> = ObservableField()
    val thumb: ObservableField<String> = ObservableField()
    val votes: ObservableField<String> = ObservableField()
    val rating: ObservableFloat = ObservableFloat()

    fun bind(restaurant: Restaurant) {
        title.set(restaurant.name)
        thumb.set(restaurant.picture)
        rating.set(restaurant.rating)
        votes.set(restaurant.votes.toString())
    }
}