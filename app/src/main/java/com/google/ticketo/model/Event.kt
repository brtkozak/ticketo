package com.google.ticketo.model

import androidx.room.*
import java.util.*

@Entity
data class Event(
    @PrimaryKey
    var id: String = "",
    var name: String? = null,
    var imageUrl: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    @Embedded
    var location: Location? = null,
    @Embedded
    var price: Price? = null,
    var lastUpdate: Date? = null,
    var favourite : Boolean = false,
    var buy : Boolean = false,
    var sell : Boolean =false)

data class Location(
    @ColumnInfo(name = "locationName")
    var name: String? = null,
    var city: String? = null,
    var address: String? = null,
    var postalCode: String? = null)

data class Price(
    var minPrice: Int? = null,
    var maxPrice: Int? = null,
    var currency: String? = null)

