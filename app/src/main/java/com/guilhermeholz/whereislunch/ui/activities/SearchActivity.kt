package com.guilhermeholz.whereislunch.ui.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.R
import com.guilhermeholz.whereislunch.SearchBinding
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.ui.adapters.SearchListAdapter
import com.guilhermeholz.whereislunch.view.SearchView
import com.guilhermeholz.whereislunch.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchActivity : LocationActivity(), SearchView {

    @Inject
    lateinit var viewModel: SearchViewModel

    private val adapter by lazy { SearchListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.component.inject(this)
        viewModel.view = this
        initBinding()
        checkForDeepLink()
    }

    private fun checkForDeepLink() {
        val pendingId = intent?.extras?.getString(RestaurantDetailActivity.idExtraKey)
        pendingId?.let {
            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra(RestaurantDetailActivity.idExtraKey, it)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun initBinding() {
        val binding: SearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.restaurantsList.layoutManager = LinearLayoutManager(this)
        binding.restaurantsList.adapter = adapter
        binding.restaurantsList.itemAnimator = null
        binding.viewModel = viewModel
    }

    override fun onLocationChanged(location: Location?) {
        location?.let { viewModel.loadRestaurants(it) }
    }

    override fun displayRestaurants(restaurants: List<Restaurant>) {
        adapter.setItems(restaurants)
    }
}
