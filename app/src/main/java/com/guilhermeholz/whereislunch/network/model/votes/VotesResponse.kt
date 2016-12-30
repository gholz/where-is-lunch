package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName
import com.guilhermeholz.whereislunch.utils.OpenForTesting

@OpenForTesting
class VotesResponse(@SerializedName("votes") val votes: List<Vote>)