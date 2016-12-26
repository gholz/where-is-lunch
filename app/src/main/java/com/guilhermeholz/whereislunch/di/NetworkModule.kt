package com.guilhermeholz.whereislunch.di

import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsDataSource
import com.guilhermeholz.whereislunch.domain.datasource.RestaurantsNetworkDataSource
import com.guilhermeholz.whereislunch.network.YelpApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides @Singleton
    fun provideYelpApi(client: OkHttpClient): YelpApi {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl("https://api.yelp.com/v3/")
                .build()
                .create(YelpApi::class.java)
    }

    @Provides @Singleton
    fun providesClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides @Singleton
    fun providesRestaurantNetworkDataSource(api: YelpApi): RestaurantsDataSource {
        return RestaurantsNetworkDataSource(api)
    }
}