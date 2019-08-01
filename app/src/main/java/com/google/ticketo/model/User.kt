package com.google.ticketo.model

class User(
    var facebookId: String?,
    var firebaseId: String?,
    var name: String?,
    var picture: String?,
    var facebookFriends: Int,
    var profileLink: String?
) {
    override fun toString(): String {
        return "User(facebookId=$facebookId, firebaseId=$firebaseId, name=$name, picture=$picture, facebookFriends=$facebookFriends, profileLink=$profileLink)"
    }
}
