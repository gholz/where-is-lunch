package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.android.services.GetChampionService
import com.guilhermeholz.whereislunch.ui.activities.RestaurantDetailActivity
import com.guilhermeholz.whereislunch.ui.activities.SearchActivity
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(AndroidModule::class, NetworkModule::class))
interface AppComponent {
    fun inject(service: GetChampionService)
    fun inject(activity: RestaurantDetailActivity)
    fun inject(activity: SearchActivity)
}