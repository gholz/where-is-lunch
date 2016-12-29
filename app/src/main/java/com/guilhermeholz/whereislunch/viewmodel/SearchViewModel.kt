package com.guilhermeholz.whereislunch.viewmodel

import android.content.SharedPreferences
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.location.Location
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.utils.isNotTheSameAs
import com.guilhermeholz.whereislunch.utils.logError
import com.guilhermeholz.whereislunch.utils.toSimpleString
import com.guilhermeholz.whereislunch.view.SearchView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel(val view: SearchView) : BaseObservable() {

    val loading: ObservableBoolean = ObservableBoolean(true)
    val empty: ObservableBoolean = ObservableBoolean(false)

    @Inject
    lateinit var repository: RestaurantsRepository

    @Inject
    lateinit var preferences: SharedPreferences

    private var previousLocation: Location? = null

    init {
        MainApp.component.inject(this)
    }

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