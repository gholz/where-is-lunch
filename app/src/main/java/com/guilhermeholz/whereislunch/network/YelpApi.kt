package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.network.model.BusinessDetail
import com.guilhermeholz.whereislunch.network.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface YelpApi {
    @GET("businesses/search?term=lunch")
    fun search(@Query("latitude") latitude: Double,
               @Query("longitude") longitude: Double): Observable<SearchResponse>

    @GET("businesses/{id}")
    fun getBusinessDetail(@Path("id") id: String): Observable<BusinessDetail>
}