package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Start(
    val dateTBA: Boolean,
    val dateTBD: Boolean,
    val dateTime: String,
    val localDate: String,
    val localTime: String,
    val noSpecificTime: Boolean,
    val timeTBA: Boolean
)