package com.google.ticketo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Remote.FirestoreRepository
import com.google.ticketo.model.User

class MyProfileViewModel : ViewModel(), CurrentUserCallback {

    private val user = MutableLiveData<User>()
    private val firestore = FirestoreRepository.getInstance()

    fun getUser() = user as LiveData<User>

    init {
        firestore.callback=this
        firestore.getCurrentUser()
    }

    override fun updateUser(user: User?) {
        this.user.value=user
    }

}
