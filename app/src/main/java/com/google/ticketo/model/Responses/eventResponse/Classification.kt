package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class Classification(
    val family: Boolean,
    val genre: Genre,
    val primary: Boolean,
    val segment: Segment,
    val subGenre: SubGenre
)