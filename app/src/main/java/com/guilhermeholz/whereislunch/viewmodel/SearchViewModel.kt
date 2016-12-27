package com.guilhermeholz.whereislunch.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.location.Location
import android.util.Log
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.view.SearchView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel(val view: SearchView) : BaseObservable() {

    val loading: ObservableBoolean = ObservableBoolean(true)
    val empty: ObservableBoolean = ObservableBoolean(false)

    private val logTag = SearchViewModel::class.java.simpleName

    @Inject
    lateinit var repository: RestaurantsRepository

    init {
        MainApp.component.inject(this)
    }

    fun loadRestaurants(location: Location) {
        repository.getRestaurants(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        view.displayRestaurants(it)
                    } else {
                        empty.set(true)
                    }
                    loading.set(false)
                }, {
                    loading.set(false)
                    Log.e(logTag, "error", it)
                })
    }
}