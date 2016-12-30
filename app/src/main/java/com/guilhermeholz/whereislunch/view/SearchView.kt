package com.guilhermeholz.whereislunch.view

import com.guilhermeholz.whereislunch.domain.model.Restaurant

interface SearchView {
    fun displayRestaurants(restaurants: List<Restaurant>)
}