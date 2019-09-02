package com.google.ticketo.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R
import com.google.ticketo.database.Local.Daos.Converters.DateConverter
import java.time.LocalDate
import java.util.*

@Entity
data class Event(
    @PrimaryKey
    var id: String = "",
    var name: String? = null,
    var imageUrl: String? = null,
    @Embedded
    var date: EventDate? = null,
    @Embedded
    var location: Location? = null,
    @Embedded
    var price: Price? = null,
    var lastUpdate: LocalDate? = null
)

data class EventDate(
    var day: Int? = null,
    var month: Int? = null,
    var year: Int? = null,
    var fullDate: String? = null,
    var startHour: String? = null
)

data class Location(
    @ColumnInfo(name = "locationName")
    var name: String? = null,
    var city: String? = null,
    var address: String? = null,
    var postalCode: String? = null
)

data class Price(
    var minPrice: Int? = null,
    var maxPrice: Int? = null,
    var currency: String? = null
)

