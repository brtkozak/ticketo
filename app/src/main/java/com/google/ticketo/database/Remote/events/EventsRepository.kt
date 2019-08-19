package com.google.ticketo.database.Remote.events

import com.google.ticketo.model.DtoConverter
import com.google.ticketo.model.Event
import com.qwerty21.musicevents.data.response.EventsResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class EventsRepository {

    private val eventsApi = EvenstApi.create()

    companion object {
        private var instance: EventsRepository? = null
        fun getInstance(): EventsRepository {
            if (instance == null)
                instance = EventsRepository()
            return instance as EventsRepository
        }
    }

    fun getEventsByCountry(country: String, size: Int, page: Int): Observable<List<Event>> {
        return eventsApi.getEventsByCountry(country, size, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { DtoConverter.eventListResponseToEventList(it) }
    }


}