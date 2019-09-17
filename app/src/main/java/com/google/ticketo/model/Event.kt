package com.google.ticketo.model

import androidx.room.*
import java.util.*

data class Event(
    var id: String = "",
    var name: String? = null,
    var imageUrl: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var locationName: String? = null,
    var city: String? = null,
    var address: String? = null,
    var postalCode: String? = null,
    var currency: String? = null,
    var minPrice: Double? = null,
    var maxPrice: Double? = null,
    var lastUpdate: Date? = null
)


