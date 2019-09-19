package com.google.ticketo.database.Local.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.ticketo.model.Event
import com.google.ticketo.model.EventDto
import com.google.ticketo.model.EventWithIntents
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

@Dao
interface EventDao {
    @Query("SELECT * FROM eventdto")
    fun getAllEvents(): LiveData<Event>

    @Query("SELECT * FROM eventdto JOIN location ON eventdto.location=location.locationName WHERE city= :city")
    fun getEventsInCity(city: String): LiveData<List<Event>>

    @Query("SELECT * FROM eventdto JOiN location ON eventdto.location=location.locationName WHERE city= :city AND lastUpdate> :lastUpdate LIMIT 1")
    fun checkUpdateWithCity(city: String, lastUpdate: Date): Event?

    @Query("SELECT * FROM eventdto JOIN location ON eventdto.location=location.locationName WHERE eventdto.id = :id AND lastUpdate> :lastUpdate LIMIT 1")
    fun checkEventUpdate(id : String, lastUpdate : Date) : Event?

    @Query("SELECT * FROM eventdto JOIN location ON eventdto.location=location.locationName WHERE startDate BETWEEN :startDate AND :endDate ")
    fun getEventsThisWeekend(startDate: Date, endDate: Date) : LiveData<List<Event>>

    @Query("SELECT * FROM eventdto WHERE  startDate BETWEEN :startDate AND :endDate AND lastUpdate> :lastUpdate LIMIT 1")
    fun checkUpdateWithDates(startDate: Date, endDate: Date, lastUpdate: Date): Event?

    @Query("SELECT * FROM eventdto JOIN location ON eventdto.location=location.locationName WHERE id = :eventId")
    fun getEvent(eventId: String): Event

    @Query("SELECT * FROM eventdto JOIN location ON eventdto.location=location.locationName WHERE id = :eventId")
    fun getEventWithDate(eventId: String): Event

    @Query("SELECT * FROM eventdto JOIN eventintents ON eventdto.id = eventintents.eventId JOIN Location ON eventdto.location=location.locationName WHERE location.city= :city")
    fun getEventWithIntentsByCity(city : String) : LiveData<List<EventWithIntents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<EventDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event : EventDto)
}