package com.guilhermeholz.whereislunch.ui.activities

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.guilhermeholz.whereislunch.R
import com.guilhermeholz.whereislunch.SearchBinding
import com.guilhermeholz.whereislunch.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel = SearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: SearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        viewModel.loadRestaurants()
    }
}
