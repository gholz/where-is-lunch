package com.guilhermeholz.whereislunch.ui.navigation

import android.content.Context
import android.content.Intent
import com.guilhermeholz.whereislunch.ui.activities.RestaurantDetailActivity
import com.guilhermeholz.whereislunch.utils.OpenForTesting

@OpenForTesting
class RestaurantNavigator(val context: Context) {
    fun navigateToDetail(id:String) {
        val intent = Intent(context, RestaurantDetailActivity::class.java)
                .putExtra(RestaurantDetailActivity.idExtraKey, id)
        context.startActivity(intent)
    }
}