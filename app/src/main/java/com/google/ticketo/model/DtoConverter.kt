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
            Log.d("looog", it.toObject(Event::class.java).toString())
        }
        return result
    }


}