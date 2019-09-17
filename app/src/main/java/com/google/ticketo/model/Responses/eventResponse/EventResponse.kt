package com.google.ticketo.model.Responses.eventResponse

import com.google.ticketo.model.Location
import java.util.*

data class EventResponse(
    val id: String ="",
    val name: String? = null,
    val imageUrl: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val location: Location? = null,
    val currency: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val lastUpdate: Date? = null
)