package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Dates(
    val access: Access,
    val spanMultipleDays: Boolean,
    val start: Start,
    val status: Status,
    val timezone: String
)