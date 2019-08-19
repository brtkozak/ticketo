package com.google.ticketo.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Remote.events.EventsRepository
import com.google.ticketo.model.Event
import com.google.ticketo.model.User

class DashboardViewModel : ViewModel() {

    private val eventsRepository = EventsRepository.getInstance()

    private val _eventsInCountry = MutableLiveData<List<Event>>()
    val eventsInCountry : LiveData<List<Event>> = _eventsInCountry


    init {
        getEvents()
    }

    private fun getEvents() {
        eventsRepository.getEventsByCountry("PL", 10, 1)
            .subscribe(
                { _eventsInCountry.value=it },
                { error -> Log.d("DashboardViewModel", error.toString()) }
            )
    }

}
