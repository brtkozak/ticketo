package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class PriceRange(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)