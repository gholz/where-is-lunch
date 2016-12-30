package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName
import com.guilhermeholz.whereislunch.utils.OpenForTesting

@OpenForTesting
class Vote(@SerializedName("restaurant_id") val id: String,
           @SerializedName("restaurant_votes") val amount: Int)