package com.guilhermeholz.whereislunch.network.model

import com.google.gson.annotations.SerializedName

class BusinessDetail(@SerializedName("id") val id: String,
                     @SerializedName("name") val name: String,
                     @SerializedName("image_url") val imageUrl: String,
                     @SerializedName("is_claimed") val isClaimed: Boolean,
                     @SerializedName("is_closed") val isClosed: Boolean,
                     @SerializedName("url") val url: String,
                     @SerializedName("price") val price: String,
                     @SerializedName("rating") val rating: Double,
                     @SerializedName("review_count") val reviewCount: Int,
                     @SerializedName("phone") val phone: String,
                     @SerializedName("photos") val photos: List<String>,
                     @SerializedName("categories") val categories: List<Category>,
                     @SerializedName("coordinates") val coordinates: Coordinates,
                     @SerializedName("location") val location: Location)