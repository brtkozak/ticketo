package com.google.ticketo.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Repository
import com.google.ticketo.model.EventInfo

class DashboardViewModel(repository: Repository) : ViewModel() {

    var eventsByCity: LiveData<List<EventInfo>> = repository.getEventsInCity("Darszawa")
    var eventsThisWeekend: LiveData<List<EventInfo>> = repository.getEventsThisWeekend()

}


