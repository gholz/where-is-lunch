package com.guilhermeholz.whereislunch.network.model.votes

import com.google.gson.annotations.SerializedName

class WinnersResponse(@SerializedName("winners") val winners: List<Winner>)