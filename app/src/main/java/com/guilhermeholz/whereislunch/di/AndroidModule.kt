package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {

    @Provides @Singleton
    fun provideRestaurantsRepository(dataSource: RestaurantsDataSource): RestaurantsRepository {
        return RestaurantsRepository(dataSource)
    }
}