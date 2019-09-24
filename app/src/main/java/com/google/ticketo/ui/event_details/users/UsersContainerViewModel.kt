package com.google.ticketo.ui.event_details.users

import android.view.ViewManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.User
import com.google.ticketo.utils.Const
import com.google.ticketo.utils.Const.BUYERS
import com.google.ticketo.utils.Const.SELLERS

class UsersContainerViewModel(val repository: Repository, val eventId: String) : ViewModel() {

    val buyers: LiveData<List<User>> = repository.getGroup(eventId, BUYERS)

    val sellers: LiveData<List<User>> = repository.getGroup(eventId, SELLERS)

    fun isCurrentUser(userId: String) = userId == repository.firestoreRepository.fireAuth.uid

}