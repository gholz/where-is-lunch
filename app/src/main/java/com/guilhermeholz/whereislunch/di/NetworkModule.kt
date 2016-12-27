package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.network.YelpApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides @Singleton
    fun provideYelpApi(): YelpApi {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.yelp.com")
                .build()
                .create(YelpApi::class.java)
    }
}