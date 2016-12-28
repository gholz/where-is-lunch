package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName

data class VotesResponse(@SerializedName("votes") val votes: List<Vote>)