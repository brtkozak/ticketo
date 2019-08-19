package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName
data class EventResponse (
    @SerializedName("_embedded")
    val embedded: EmbeddedX,
    @SerializedName("_links")
    val links:  Links,
    val classifications:  List<Classification>,
    val dates:  Dates,
    val id: String,
    val images:  List<Image>,
    val locale: String,
    val name: String,
    val priceRanges:  List<PriceRange>,
    val promoter:  Promoter,
    val promoters:  List<Promoter>,
    val sales:  Sales,
    val seatmap:  Seatmap,
    val test: Boolean,
    val type: String,
    val url: String
)
