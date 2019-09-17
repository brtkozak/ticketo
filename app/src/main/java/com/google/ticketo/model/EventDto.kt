package com.google.ticketo.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("locationName"),
            childColumns = arrayOf("location")
        )
    )
)
data class EventDto(
    @PrimaryKey
    var id: String = "",
    var name: String? = null,
    var imageUrl: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var location: String? = null,
    var currency: String? = null,
    var minPrice: Double? = null,
    var maxPrice: Double? = null,
    var lastUpdate: Date? = null
)
