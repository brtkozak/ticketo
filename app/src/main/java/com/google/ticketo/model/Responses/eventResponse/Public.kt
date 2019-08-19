package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Public(
    val endDateTime: String,
    val startDateTime: String,
    val startTBD: Boolean
)