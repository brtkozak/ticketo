package com.google.ticketo.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event

class DashboardViewModel(repository: Repository) : ViewModel() {

    var eventsByCity: LiveData<List<Event>> = repository.getEventsInCity("Wrocław")
    var eventsThisWeekend: LiveData<List<Event>> = repository.getEventsThisWeekend()

}


