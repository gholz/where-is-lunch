package com.guilhermeholz.whereislunch.domain.model

data class Restaurant(val id: String,
                      val name: String,
                      val picture: String,
                      val rating: Float,
                      val votes: Int)