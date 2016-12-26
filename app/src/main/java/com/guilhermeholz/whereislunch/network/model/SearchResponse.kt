package com.guilhermeholz.whereislunch.network.model

import com.google.gson.annotations.SerializedName

class SearchResponse(@SerializedName("total") val total: Int,
                     @SerializedName("businesses") val businesses: List<Business>)
