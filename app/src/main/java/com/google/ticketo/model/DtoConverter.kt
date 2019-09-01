package com.google.ticketo.model

import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import com.google.ticketo.model.Responses.userResponse.UserResponse
import com.qwerty21.musicevents.data.response.EventResponse
import com.qwerty21.musicevents.data.response.EventsResponse
import java.time.LocalDate

object DtoConverter {


    fun querySnapshotToEventsList(querySnapshot: QuerySnapshot) : List<Event>{
        val result = mutableListOf<Event>()
        querySnapshot.forEach {
            result.add(it.toObject(Event::class.java))
        }
        return result
    }

//    fun eventListResponseToEventList(eventsResponse: EventsResponse): List<Event> {
//        val result = mutableListOf<Event>()
//
//        eventsResponse.embedded.eventResponses.forEach {
//            result.add(eventResponseToEvent(it))
//        }
//        return result
//    }
//
//    fun eventResponseToEvent(eventResponse: EventResponse): Event {
//        var counter = 0
//        var image = eventResponse.images[counter]
//        while (image.ratio != "16_9" || counter > eventResponse.images.size) {
//            counter++
//            image = eventResponse.images[counter]
//        }
//
//        return Event(
//            eventResponse.id,
//            eventResponse.name,
//            LocalDate.parse(eventResponse.dates.start.localDate),
//            eventResponse.embedded.venues[0].name,
//            image.url
//        )
//    }


}