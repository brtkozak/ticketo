package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName


data class EmbeddedX(
    val attractions: List<Attraction>,
    val venues: List<Venue>
)