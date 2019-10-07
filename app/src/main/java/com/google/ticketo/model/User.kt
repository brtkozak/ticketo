package com.google.ticketo.model

data class User(
    var facebookId: String? = null,
    var firebaseId: String? = null,
    var name: String? = null,
    var picture: String? = null,
    var facebookFriends: Int? = null,
    var profileLink: String? = null,
    var recommendations: List<String>?=null,
    var warnings: List<String>?=null
)


