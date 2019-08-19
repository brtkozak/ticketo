package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class UpcomingEvents(
    @SerializedName("_total")
    val total: Int,
    @SerializedName("mfx-pl")
    val mfxPl: Int
)