package com.google.ticketo.model

import java.util.*

data class Comment(
    val content: String? = null,
    val user : User? = null,
    val date: Date = Date(System.currentTimeMillis()),
    var id: String? = ""
)