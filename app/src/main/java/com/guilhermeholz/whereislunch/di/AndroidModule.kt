package com.guilhermeholz.whereislunch.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.datasource.RestaurantsNetworkDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(val context: Context) {

    @Provides @Singleton
    fun provideRestaurantsRepository(dataSource: RestaurantsDataSource): RestaurantsRepository {
        return RestaurantsRepository(dataSource)
    }

    @Provides @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides @Singleton
    fun providesRestaurantsDataSource(api: YelpApi, preferences: SharedPreferences): RestaurantsDataSource {
        return RestaurantsNetworkDataSource(api, preferences)
    }
}