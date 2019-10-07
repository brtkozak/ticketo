package com.google.ticketo.model

import androidx.room.Ignore
import java.util.*

data class EventInfo(
    var id: String = "",
    var name: String? = null,
    var imageUrl: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var currency: String? = null,
    var minPrice: Double? = null,
    var maxPrice: Double? = null,
    var lastUpdate: Date? = null,
    var buy: Boolean? = false,
    var sell: Boolean? = false,
    var favourite: Boolean? = false
)


