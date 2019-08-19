package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName
data class Embedded(
    @SerializedName("events")
    val eventResponses: List<EventResponse>
)