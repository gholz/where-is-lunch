package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName

data class Vote(@SerializedName("restaurant_id") val id: String,
                @SerializedName("restaurant_votes") val amount: Int)