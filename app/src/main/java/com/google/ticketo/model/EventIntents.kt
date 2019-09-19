package com.google.ticketo.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = EventDto::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("eventId")
        )
    )
)
data class EventIntents(
    @PrimaryKey(autoGenerate = true)
    val intentsId: Int = 0,
    val eventId: String,
    var buy: Boolean = false,
    var sell: Boolean = false,
    var favourite: Boolean = false
)

