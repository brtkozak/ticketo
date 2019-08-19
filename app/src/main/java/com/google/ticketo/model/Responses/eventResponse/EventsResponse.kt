package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("_embedded")
    val embedded: Embedded,
    @SerializedName("_links")
    val links: Links,
    val page: Page
)