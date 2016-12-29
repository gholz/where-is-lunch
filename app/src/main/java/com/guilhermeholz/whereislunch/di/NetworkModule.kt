package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.network.VotingApi
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

    @Provides @Singleton
    fun provideVotingApi(): VotingApi {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.0.108/whereislunch/api/")
                .build()
                .create(VotingApi::class.java)
    }
}