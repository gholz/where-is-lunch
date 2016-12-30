package com.guilhermeholz.whereislunch.network.model.yelp

import com.google.gson.annotations.SerializedName
import com.guilhermeholz.whereislunch.utils.OpenForTesting

@OpenForTesting
class Coordinates(@SerializedName("latitude") val latitude: Double,
                  @SerializedName("longitude") val longitude: Double)
