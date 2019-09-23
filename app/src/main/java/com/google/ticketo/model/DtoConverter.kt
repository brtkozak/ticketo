package com.google.ticketo.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.ticketo.model.Responses.eventResponse.EventResponse
import com.google.ticketo.utils.Const
import java.util.*

object DtoConverter {

    fun querySnapshotToEvent(querySnapshot: QuerySnapshot) : Pair<EventDto?, Location?> {
        var eventResponse : EventResponse? = null
        querySnapshot.forEach{
            eventResponse=it.toObject(EventResponse::class.java)
        }
        val event = eventResponseToEventDto(eventResponse)
        val location = eventResponseToLocation(eventResponse)
        return Pair(event, location)
    }

    fun querySnapshotToEventsList(querySnapshot: QuerySnapshot) : Pair<List<EventDto>, List<Location>> {
        val events = mutableListOf<EventDto>()
        val locations = mutableListOf<Location>()
        querySnapshot.forEach {
            val temp = it.toObject(EventResponse::class.java)
            eventResponseToEventDto(temp)?.let { event -> events.add(event) }
            eventResponseToLocation(temp)?.let { location -> locations.add(location) }
        }
        return Pair(events, locations)
    }

    private fun eventResponseToEventDto(eventResponse: EventResponse?): EventDto? =
        eventResponse?.let {
            EventDto(
                it.id,
                it.name,
                it.imageUrl,
                it.startDate,
                it.endDate,
                it.location?.locationName,
                it.currency,
                it.minPrice,
                it.maxPrice,
                Date()
            )
        }


    private fun eventResponseToLocation(eventResponse: EventResponse?): Location? =
        eventResponse?.let{
            Location(
                it.location!!.locationName,
                it.location.city,
                it.location.address,
                it.location.postalCode
            )
        }


    fun querySnapshoTtoLisOfUsers(querySnapshot: QuerySnapshot) : List<User> {
        val result = mutableListOf<User>()
        querySnapshot.forEach {
            result.add(it.toObject(User::class.java))
        }
        return result
    }

    fun documentSnapshotToUser(documentSnapshot : DocumentSnapshot) : User {
        return documentSnapshot.toObject(User::class.java)!!
    }

    fun querySnapshotToEventsNameList(querySnapshot: QuerySnapshot) : List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        querySnapshot.forEach {
            val temp = it.toObject(EventResponse::class.java)
            result.add(Pair(temp.name!!, temp.id))
        }
        return result
    }

    fun querySnapshotToEventsLocationList(querySnapshot: QuerySnapshot) : List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        querySnapshot.forEach {
            val temp = it.toObject(EventResponse::class.java)
            result.add(Pair(temp.location?.city!!, ""))
        }
        return result.distinct()
    }

}