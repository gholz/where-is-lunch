package com.guilhermeholz.whereislunch.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.location.Location
import android.util.Log
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.view.SearchView
import javax.inject.Inject

class SearchViewModel(val view: SearchView) : BaseObservable() {

    val loading: ObservableBoolean = ObservableBoolean(true)
    val empty: ObservableBoolean = ObservableBoolean(false)

    @Inject
    lateinit var repository: RestaurantsRepository

    init {
        MainApp.component.inject(this)
    }

    fun loadRestaurants(location: Location) {
        repository.getRestaurants(location)
                .subscribe({
                    if (it.isNotEmpty()) {
                        view.displayRestaurants(it)
                    } else {
                        empty.set(true)
                    }
                    loading.set(false)
                }, {
                    loading.set(false)
                    Log.e(SearchViewModel::class.java.simpleName, "error", it)
                })
    }
}