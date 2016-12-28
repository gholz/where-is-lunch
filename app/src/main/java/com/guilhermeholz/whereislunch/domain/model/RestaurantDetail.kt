package com.guilhermeholz.whereislunch.domain.model

data class RestaurantDetail(val name: String,
                            val phone: String,
                            val image: String,
                            val rating: Float,
                            val address: String,
                            val votes: Int)