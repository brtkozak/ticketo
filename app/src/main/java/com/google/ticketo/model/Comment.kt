package com.google.ticketo.model

import java.util.*

data class Comment(
    val content: String? = null,
    val userdId: String? = null,
    val userName: String? = null,
    val userPic: String? = null,
    val date: Date = Date(System.currentTimeMillis())
)