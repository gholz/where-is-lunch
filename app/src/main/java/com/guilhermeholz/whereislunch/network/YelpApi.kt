package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.network.model.AuthResponse
import com.guilhermeholz.whereislunch.network.model.BusinessDetail
import com.guilhermeholz.whereislunch.network.model.SearchResponse
import retrofit2.http.*
import rx.Observable

interface YelpApi {
    @GET("/v3/businesses/search?term=lunch")
    fun search(@Header("Authorization") authHeader: String,
               @Query("latitude") latitude: Double,
               @Query("longitude") longitude: Double): Observable<SearchResponse>

    @GET("/v3/businesses/{id}")
    fun getBusinessDetail(@Header("Authorization") authHeader: String,
                          @Path("id") id: String): Observable<BusinessDetail>

    @FormUrlEncoded
    @POST("/oauth2/token")
    fun getAuthToken(@Field("grant_type") type: String,
                     @Field("client_id") id: String,
                     @Field("client_secret") secret: String): Observable<AuthResponse>
}