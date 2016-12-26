package com.guilhermeholz.whereslunch.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.os.Handler

class SearchViewModel : BaseObservable() {
    val loading: ObservableBoolean = ObservableBoolean(true)
    val empty: ObservableBoolean = ObservableBoolean(false)

    fun loadRestaurants() {
        Handler().postDelayed({
            loading.set(false)
            empty.set(true)
        }, 5000)
    }
}