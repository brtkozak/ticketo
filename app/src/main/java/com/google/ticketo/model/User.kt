package com.google.ticketo.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R

data class User(
    var facebookId: String? = null,
    var firebaseId: String? = null,
    var name: String? = null,
    var picture: String? = null,
    var facebookFriends: Int? = null,
    var profileLink: String? = null
)


