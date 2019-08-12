package com.google.ticketo.ui.profile

import com.google.ticketo.model.User

interface CurrentUserCallback {
    fun updateUser(user: User?)
}