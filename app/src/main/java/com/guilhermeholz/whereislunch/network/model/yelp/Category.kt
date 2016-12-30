package com.guilhermeholz.whereislunch.network.model.yelp

import com.google.gson.annotations.SerializedName
import com.guilhermeholz.whereislunch.utils.OpenForTesting

@OpenForTesting
class Category(@SerializedName("alias") val alias: String,
               @SerializedName("title") val title: String)
