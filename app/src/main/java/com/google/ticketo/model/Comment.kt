package com.google.ticketo.model

import java.util.*

data class Comment(
    val content: String? = null,
    val userId: String? = null,
    val userName: String? = null,
    val userPic: String? = null,
    val date: Date = Date(System.currentTimeMillis()),
    var id: String? = ""
)