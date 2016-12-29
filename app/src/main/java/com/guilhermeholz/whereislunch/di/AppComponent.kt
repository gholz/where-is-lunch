package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.android.services.GetChampionService
import com.guilhermeholz.whereislunch.viewmodel.RestaurantDetailViewModel
import com.guilhermeholz.whereislunch.viewmodel.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(AndroidModule::class, NetworkModule::class))
interface AppComponent {
    fun inject(viewModel: SearchViewModel)
    fun inject(viewModel: RestaurantDetailViewModel)
    fun inject(service: GetChampionService)
    fun inject(application: MainApp)
}