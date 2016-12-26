package com.guilhermeholz.whereslunch.model

data class Restaurant(val name: String, val picture: String, val rating: Float, var votes: Int = 0)