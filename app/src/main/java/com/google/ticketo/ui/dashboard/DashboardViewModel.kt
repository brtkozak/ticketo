package com.google.ticketo.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import com.google.ticketo.utils.Const.CITY

class DashboardViewModel(repository: Repository) : ViewModel() {

    var eventsByCity: LiveData<List<Event>> = repository.getEventsInCity(CITY)
    var eventsThisWeekend: LiveData<List<Event>> = repository.getEventsThisWeekend()
    var discoverEvents : LiveData<List<Event>> = repository.discoverEvents(CITY)
//    init {
//        repository.firestoreRepository.insertEvent()
//    }
}


