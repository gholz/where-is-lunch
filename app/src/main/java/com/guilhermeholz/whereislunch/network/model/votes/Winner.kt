package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName

class Winner(@SerializedName("restaurant_id") val id: String,
             @SerializedName("pool_date") val date: String)