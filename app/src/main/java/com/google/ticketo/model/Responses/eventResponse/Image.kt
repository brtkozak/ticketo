package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Image(
    val fallback: Boolean,
    val height: Int,
    val ratio: String,
    val url: String,
    val width: Int
)