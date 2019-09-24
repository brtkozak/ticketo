package com.google.ticketo.ui.event_details.users

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.database.Repository
import com.google.ticketo.ui.event_details.EventDetailsViewModel

class UserContainerFactory(val context : Context, val eventId : String)  : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersContainerViewModel(Repository.getInstance(context), eventId) as T
    }
}