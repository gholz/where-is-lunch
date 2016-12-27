package com.guilhermeholz.whereislunch.network.model

import com.google.gson.annotations.SerializedName

class AuthResponse(@SerializedName("access_token") val accessToken: String,
                   @SerializedName("token_type") val tokenType: String,
                   @SerializedName("expires_in") val expiresIn: Long)