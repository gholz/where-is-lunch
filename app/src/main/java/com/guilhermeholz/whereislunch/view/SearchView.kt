package com.guilhermeholz.whereislunch.view

import com.guilhermeholz.whereislunch.domain.model.Restaurant

/**
 * Created by fenrir on 26/12/16.
 */
interface SearchView {
    fun displayRestaurants(restaurants: List<Restaurant>)
}