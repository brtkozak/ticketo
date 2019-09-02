package com.google.ticketo.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Remote.events.EventsRepository
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import com.google.ticketo.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class DashboardViewModel(repository: Repository) : ViewModel() {


    lateinit var events: LiveData<List<Event>>

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    init {
        events=repository.getEventsInCity("Wrocław")
    }

    ////////////////////////
//
//    private val firestore = FirestoreRepository.getInstance()
//    private val _events = MutableLiveData<List<Event>>()
//    val events: LiveData<List<Event>> = _events
//
//    private val _loading = MutableLiveData<Boolean> (true)
//    val loading : LiveData<Boolean> = _loading
//
//    init {
//        getEvents()
//    }
//
//    private fun getEvents() {
//            firestore.getAllEvents()
//                .subscribe { it ->
//                    _events.value = it
//                    _loading.value=false
//                }
//    }
//

    ///////////////////////


//    private val eventsRepository = EventsRepository.getInstance()
//
//    private val _eventsMap = MutableLiveData<HashMap<EventCategory, List<Event>>>()
//    val eventsMap: LiveData<HashMap<EventCategory, List<Event>>> = _eventsMap
//    private var eventsMapLocal = HashMap<EventCategory, List<Event>>()
//
//    private val _progress = MutableLiveData<Boolean>(true)
//    val progress: LiveData<Boolean> = _progress
//
//    init {
////        getAllEvents()
//    }
//
//    private fun getAllEvents() {
//        eventsMapLocal.clear()
//        Observable.zip(
//            eventsRepository.getEventsByCountry("PL", 10, 1),
//            eventsRepository.getEventsByCountry("US", 10, 1),
//            BiFunction<List<Event>, List<Event>, Any>
//            { e1, e2 -> addEvents(e1, e2) })
//            .subscribe {
//                _progress.value = false
//            }
//    }
//
//    private fun addEvents(e1: List<Event>, e2: List<Event>) {
//        eventsMapLocal[EventCategory.country] = e1
//        eventsMapLocal[EventCategory.city] = e2
//        _eventsMap.value = eventsMapLocal
//    }
}


