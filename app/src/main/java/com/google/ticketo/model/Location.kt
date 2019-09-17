package com.google.ticketo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey
    var locationName: String ="",
    var city: String? = null,
    var address: String? = null,
    var postalCode: String? = null
)
