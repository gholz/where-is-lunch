package com.guilhermeholz.whereislunch.network.model

import com.google.gson.annotations.SerializedName

class Coordinates(@SerializedName("latitude") val latitude: Double,
                  @SerializedName("longitude") val longitude: Double)
