package com.guilhermeholz.whereislunch.network.model.yelp

import com.google.gson.annotations.SerializedName

class Business(@SerializedName("rating") val rating: Float,
               @SerializedName("price") val price: String,
               @SerializedName("phone") val phone: String,
               @SerializedName("id") val id: String,
               @SerializedName("is_closed") val isClosed: Boolean,
               @SerializedName("categories") val categories: List<Category>,
               @SerializedName("review_count") val reviewCount: Int,
               @SerializedName("name") val name: String,
               @SerializedName("url") val url: String,
               @SerializedName("coordinates") val coordinates: Coordinates,
               @SerializedName("image_url") val imageUrl: String,
               @SerializedName("location") val location: Location,
               @SerializedName("distance") val distance: Float)
