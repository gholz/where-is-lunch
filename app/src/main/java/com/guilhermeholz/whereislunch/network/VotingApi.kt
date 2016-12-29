package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.network.model.votes.Vote
import com.guilhermeholz.whereislunch.network.model.votes.VotesResponse
import com.guilhermeholz.whereislunch.network.model.votes.WinnersResponse
import retrofit2.http.*
import rx.Observable

interface VotingApi {
    @GET("votes.php")
    fun getVotes(@Query("date") date: String): Observable<VotesResponse>

    @GET("votes.php")
    fun getVotesById(@Query("id") id: String, @Query("date") date: String): Observable<Vote>

    @FormUrlEncoded
    @POST("votes.php")
    fun vote(@Field("id") id: String, @Field("date") date: String): Observable<Vote>

    @GET("winners.php")
    fun getWinners(): Observable<WinnersResponse>
}