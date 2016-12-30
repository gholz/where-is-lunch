package com.guilhermeholz.whereislunch.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.network.VotingApi
import com.guilhermeholz.whereislunch.network.YelpApi
import com.guilhermeholz.whereislunch.network.datasource.RestaurantsNetworkDataSource
import com.guilhermeholz.whereislunch.utils.DateManager
import com.guilhermeholz.whereislunch.viewmodel.RestaurantDetailViewModel
import com.guilhermeholz.whereislunch.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(val context: Context) {

    @Provides @Singleton
    fun providesDateManager(): DateManager {
        return DateManager()
    }

    @Provides
    fun providesSearchViewModel(preferences: SharedPreferences, repository: RestaurantsRepository): SearchViewModel {
        return SearchViewModel(preferences, repository)
    }

    @Provides
    fun providesRestaurantDetailViewModel(repository: RestaurantsRepository): RestaurantDetailViewModel {
        return RestaurantDetailViewModel(repository)
    }

    @Provides @Singleton
    fun provideRestaurantsRepository(dataSource: RestaurantsDataSource, dateManager: DateManager, preferences: SharedPreferences): RestaurantsRepository {
        return RestaurantsRepository(dataSource, dateManager, preferences)
    }

    @Provides @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides @Singleton
    fun providesRestaurantsDataSource(yelpApi: YelpApi, votingApi: VotingApi, preferences: SharedPreferences): RestaurantsDataSource {
        return RestaurantsNetworkDataSource(yelpApi, votingApi, preferences)
    }
}