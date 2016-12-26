package com.guilhermeholz.whereislunch.network.model

import com.google.gson.annotations.SerializedName

class Category(@SerializedName("alias") val alias: String,
               @SerializedName("title") val title: String)
