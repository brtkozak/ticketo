package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class LinksX(
    val attractions: List<AttractionX>,
    val self: Self,
    val venues: List<VenueX>
)