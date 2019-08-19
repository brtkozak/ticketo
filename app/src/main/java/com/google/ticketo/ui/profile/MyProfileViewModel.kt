package com.google.ticketo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.User

class MyProfileViewModel : ViewModel(), CurrentUserCallback {

    private val _user = MutableLiveData<User>()
    private val firestore = FirestoreRepository.getInstance()

    val user : LiveData<User> = _user

    init {
        firestore.callback=this
        firestore.getCurrentUser()
    }

    override fun updateUser(user: User?) {
        this._user.value=user
    }

}
