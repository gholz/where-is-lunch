package com.guilhermeholz.whereislunch.ui.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guilhermeholz.whereislunch.R
import com.guilhermeholz.whereislunch.databinding.SearchItemBinding
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.viewmodel.SearchItemViewModel

class SearchListAdapter(context: Context) : RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    val items: MutableList<Restaurant> = mutableListOf()

    override fun onBindViewHolder(holder: SearchItemViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(inflater.inflate(R.layout.item_search, parent, false))
    }

    fun setItems(restaurants: List<Restaurant>) {
        val change = restaurants.size == items.size
        items.clear()
        items.addAll(restaurants)
        if (change) {
            notifyItemRangeChanged(0, restaurants.size)
        } else {
            notifyDataSetChanged()
        }
    }

    inner class SearchItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val viewModel: SearchItemViewModel = SearchItemViewModel(view.context)
        val binding: SearchItemBinding = DataBindingUtil.bind(view)

        init {
            binding.viewModel = viewModel
        }

        fun bind(restaurant: Restaurant) {
            viewModel.bind(restaurant)
        }
    }
}