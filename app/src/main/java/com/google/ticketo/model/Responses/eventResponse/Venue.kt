package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Venue(
    @SerializedName("_links")
    val links: Links,
    val address: Address,
    val city: City,
    val country: Country,
    val id: String,
    val locale: String,
    val location: Location,
    val name: String,
    val postalCode: String,
    val state: State,
    val test: Boolean,
    val timezone: String,
    val type: String,
    val upcomingEvents: UpcomingEvents,
    val url: String
)