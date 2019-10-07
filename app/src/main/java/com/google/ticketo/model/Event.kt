package com.google.ticketo.model

import androidx.room.Embedded
import androidx.room.Ignore

data class Event(
    @Embedded
    var eventInfo : EventInfo ?=null,
    @Embedded
    var location : Location? =null,
    @Ignore
    var buyers : List<User> ? =null,
    @Ignore
    var sellers : List<User> ? =null,
    @Ignore
    var comments : List<Comment> ?=null
)