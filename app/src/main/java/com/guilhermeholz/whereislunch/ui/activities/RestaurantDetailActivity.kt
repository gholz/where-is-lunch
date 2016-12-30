package com.guilhermeholz.whereislunch.ui.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.R
import com.guilhermeholz.whereislunch.databinding.RestaurantDetailBinding
import com.guilhermeholz.whereislunch.viewmodel.RestaurantDetailViewModel
import javax.inject.Inject

class RestaurantDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: RestaurantDetailViewModel

    companion object {
        val idExtraKey = "restaurant_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.component.inject(this)
        initBinding()
        retrieveExtras()
    }

    private fun retrieveExtras() {
        val restaurantId: String? = intent.extras.getString(idExtraKey)
        restaurantId?.let {
            viewModel.loadRestaurant(it)
        }
    }

    private fun initBinding() {
        val binding: RestaurantDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail)
        binding.viewModel = viewModel
    }
}