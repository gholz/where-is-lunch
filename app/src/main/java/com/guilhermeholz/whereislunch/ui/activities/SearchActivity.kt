package com.guilhermeholz.whereislunch.ui.activities

import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.guilhermeholz.whereislunch.R
import com.guilhermeholz.whereislunch.SearchBinding
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.ui.adapters.SearchListAdapter
import com.guilhermeholz.whereislunch.view.SearchView
import com.guilhermeholz.whereislunch.viewmodel.SearchViewModel

class SearchActivity : LocationActivity(), SearchView {

    private val viewModel by lazy { SearchViewModel(this) }
    private val adapter by lazy { SearchListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        val binding: SearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.restaurantsList.layoutManager = LinearLayoutManager(this)
        binding.restaurantsList.adapter = adapter
        binding.viewModel = viewModel
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            viewModel.loadRestaurants(location)
        }
    }

    override fun displayRestaurants(restaurants: List<Restaurant>) {
        adapter.setItems(restaurants)
    }
}
