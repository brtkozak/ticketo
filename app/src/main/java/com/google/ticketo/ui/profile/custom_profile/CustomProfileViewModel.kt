package com.google.ticketo.ui.profile.custom_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.User

class CustomProfileViewModel(val repository : Repository, userId : String) : ViewModel() {

    val user : LiveData<User> = repository.getUser(userId)

}
