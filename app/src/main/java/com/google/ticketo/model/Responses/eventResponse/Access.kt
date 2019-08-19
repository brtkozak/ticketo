package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Access(
    val endApproximate: Boolean,
    val startApproximate: Boolean,
    val startDateTime: String
)