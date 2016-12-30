package com.guilhermeholz.whereislunch.viewmodel

import android.content.SharedPreferences
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.location.Location
import android.support.annotation.VisibleForTesting
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.utils.isNotTheSameAs
import com.guilhermeholz.whereislunch.utils.logError
import com.guilhermeholz.whereislunch.utils.toSimpleString
import com.guilhermeholz.whereislunch.view.SearchView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchViewModel(val preferences: SharedPreferences,
                      val repository: RestaurantsRepository) : BaseObservable() {

    val loading: ObservableBoolean = ObservableBoolean(true)
    val error: ObservableBoolean = ObservableBoolean(false)
    val empty: ObservableBoolean = ObservableBoolean(false)

    lateinit var view: SearchView

    @VisibleForTesting
    private var previousLocation: Location? = null

    fun loadRestaurants(location: Location) {
        if (location.isNotTheSameAs(previousLocation)) {
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
                        error.set(true)
                        logError(it)
                    })
            previousLocation = location
            preferences.edit().putString("lastLocation", previousLocation?.toSimpleString()).apply()
        }
    }

    fun onStart() {
        previousLocation?.let {
            previousLocation = null
            loadRestaurants(it)
        }
    }
}