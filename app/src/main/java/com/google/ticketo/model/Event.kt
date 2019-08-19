package com.google.ticketo.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R
import java.time.LocalDate
import java.util.*

data class Event(
    var id: String? = null,
    var name: String? = null,
    var date: LocalDate? = null,
    var place: String? = null,
    var image: String? = null
){
    companion object {
        @JvmStatic
        @BindingAdapter("eventPic")
        fun ImageView.eventPic(imageUrl: String?) {
            Glide.with(this.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions.centerCropTransform())
                .into(this)
        }
    }
}