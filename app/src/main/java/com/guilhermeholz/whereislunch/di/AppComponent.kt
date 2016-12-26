package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.viewmodel.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(AndroidModule::class, NetworkModule::class))
interface AppComponent {
    fun inject(viewModel: SearchViewModel)
}