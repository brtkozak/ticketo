package com.google.ticketo.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Remote.events.EventsRepository
import com.google.ticketo.model.Event
import com.google.ticketo.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class DashboardViewModel : ViewModel() {

    private val eventsRepository = EventsRepository.getInstance()

    private val _eventsMap = MutableLiveData<HashMap<EventCategory, List<Event>>>()
    val eventsMap: LiveData<HashMap<EventCategory, List<Event>>> = _eventsMap
    private var eventsMapLocal = HashMap<EventCategory, List<Event>>()

    private val _progress = MutableLiveData<Boolean>(true)
    val progress: LiveData<Boolean> = _progress

    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        eventsMapLocal.clear()
        Observable.zip(
            eventsRepository.getEventsByCountry("PL", 10, 1),
            eventsRepository.getEventsByCountry("US", 10, 1),
            BiFunction<List<Event>, List<Event>, Any>
            { e1, e2 -> addEvents(e1, e2) })
            .subscribe {
                _progress.value = false
            }
    }

    private fun addEvents(e1: List<Event>, e2: List<Event>) {
        eventsMapLocal[EventCategory.country] = e1
        eventsMapLocal[EventCategory.city] = e2
        _eventsMap.value = eventsMapLocal
    }
}


