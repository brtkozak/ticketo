package com.google.ticketo.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R

@BindingAdapter("app:profilePic")
fun ImageView.profilePic(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .apply(RequestOptions.circleCropTransform())
        .override(350, 350)
        .into(this)
}

@BindingAdapter("app:eventPic")
fun ImageView.eventPic(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .apply(RequestOptions.centerCropTransform())
        .into(this)
}

@BindingAdapter("app:userPic")
fun ImageView.userPic(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

