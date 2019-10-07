package com.google.ticketo.ui.search.eventsByCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventsByCityViewModel(val repository : Repository, val city : String) : ViewModel() {

    val events : LiveData<List<Event>> = repository.getEventsWithIntentsByCity(city)

    fun updateFavourites(eventId : String, state : Boolean){
        repository.updateFavourites(eventId, state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
