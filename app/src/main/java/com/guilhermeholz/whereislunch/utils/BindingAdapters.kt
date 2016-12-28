package com.guilhermeholz.whereislunch.utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("bind:visible")
fun setVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).crossFade().into(view)
}