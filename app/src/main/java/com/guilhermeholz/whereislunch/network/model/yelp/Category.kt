package com.guilhermeholz.whereislunch.network.model.yelp

import com.google.gson.annotations.SerializedName

class Category(@SerializedName("alias") val alias: String,
               @SerializedName("title") val title: String)
