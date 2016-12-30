package com.guilhermeholz.whereislunch.network.model.yelp

import com.google.gson.annotations.SerializedName
import com.guilhermeholz.whereislunch.utils.OpenForTesting

@OpenForTesting
class SearchResponse(@SerializedName("total") val total: Int,
                     @SerializedName("businesses") val businesses: List<Business>)
