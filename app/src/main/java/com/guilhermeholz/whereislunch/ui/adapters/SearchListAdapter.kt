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

    val inflater: LayoutInflater
    val items: MutableList<Restaurant> = mutableListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(inflater.inflate(R.layout.item_search, parent, false))
    }

    fun setItems(restaurants: List<Restaurant>) {
        items.clear()
        items.addAll(restaurants)
        notifyDataSetChanged()
    }

    inner class SearchItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val viewModel: SearchItemViewModel = SearchItemViewModel()
        val binding: SearchItemBinding

        init {
            binding = DataBindingUtil.bind(view)
            binding.viewModel = viewModel
        }

        fun bind(restaurant: Restaurant) {
            viewModel.bind(restaurant)
        }
    }
}