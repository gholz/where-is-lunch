package com.guilhermeholz.whereslunch.utils

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("bind:visible")
fun setVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}