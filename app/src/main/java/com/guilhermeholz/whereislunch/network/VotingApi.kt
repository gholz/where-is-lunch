package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.network.model.votes.Vote
import com.guilhermeholz.whereislunch.network.model.votes.VotesResponse
import retrofit2.http.*
import rx.Observable

interface VotingApi {
    @GET("/api/votes.php")
    fun getVotes(@Query("date") date: String): Observable<VotesResponse>

    @GET("/api/votes.php")
    fun getVotesById(@Query("id") id: String, @Query("date") date: String): Observable<Vote>

    @FormUrlEncoded
    @POST("/api/votes.php")
    fun vote(@Field("id") id: String, @Field("date") date: String): Observable<Vote>

    @GET("/api/winners.php")
    fun getWinner(@Query("date") date: String): Observable<Vote>

    @GET("/api/winners.php")
    fun getWinners(): Observable<VotesResponse>
}