package com.google.ticketo.Model.Responses

data class UserResponse(
    val friends: Friends,
    val id: String,
    val link: String,
    val name: String,
    val picture: Picture
)