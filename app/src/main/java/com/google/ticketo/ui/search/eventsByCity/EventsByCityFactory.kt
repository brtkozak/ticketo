package com.google.ticketo.ui.search.eventsByCity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.database.Repository
import com.google.ticketo.ui.event_details.EventDetailsViewModel

class EventsByCityFactory(val context : Context, val city : String)  : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventsByCityViewModel(Repository.getInstance(context), city) as T
    }
}