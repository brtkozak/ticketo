package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Attraction(
    @SerializedName("_links")
    val links: LinksX,
    val classifications: List<ClassificationX>,
    val externalLinks: ExternalLinks,
    val id: String,
    val images: List<Image>,
    val locale: String,
    val name: String,
    val test: Boolean,
    val type: String,
    val upcomingEvents: UpcomingEvents,
    val url: String
)