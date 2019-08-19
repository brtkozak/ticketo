package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Page(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)