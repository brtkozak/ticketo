package com.google.ticketo.ui.event_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _event = MutableLiveData<Event> ()
    val event = _event

    fun setEvent(eventId : String){
        repository.getEvent(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _event.value=it
            }
    }
}
