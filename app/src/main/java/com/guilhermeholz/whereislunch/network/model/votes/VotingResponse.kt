package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName

class VotingResponse(@SerializedName("restaurant_votes") val amount: Int)