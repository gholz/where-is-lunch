package com.guilhermeholz.whereislunch.network.model.yelp

import com.google.gson.annotations.SerializedName

class Location(@SerializedName("city") val city: String,
               @SerializedName("country") val country: String,
               @SerializedName("address2") val address2: String,
               @SerializedName("address3") val address3: String,
               @SerializedName("state") val state: String,
               @SerializedName("address1") val address1: String,
               @SerializedName("zip_code") val zipCode: String)
