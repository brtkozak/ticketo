package com.google.ticketo.ui.event_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.database.Repository

class EventDetailsFactory(val context : Context, val eventId : String)  : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventDetailsViewModel(Repository.getInstance(context), eventId) as T
    }
}