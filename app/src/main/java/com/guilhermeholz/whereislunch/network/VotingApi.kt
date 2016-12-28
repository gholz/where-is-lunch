package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.network.model.votes.Vote
import com.guilhermeholz.whereislunch.network.model.votes.VotesResponse
import com.guilhermeholz.whereislunch.network.model.votes.VotingResponse
import com.guilhermeholz.whereislunch.network.model.votes.WinnersResponse
import retrofit2.http.*
import rx.Observable

interface VotingApi {
    @GET("/votes")
    fun getVotes(@Query("date") date: String): Observable<VotesResponse>

    @GET("/votes/{restaurant_id}")
    fun getVotesById(@Path("restaurant_id") id: String, @Query("date") date: String): Observable<Vote>

    @GET("/winners")
    fun getWinners(): Observable<WinnersResponse>

    @FormUrlEncoded
    @POST("/votes")
    fun vote(@Field("id") id: String, @Field("date") date: String): Observable<VotingResponse>
}