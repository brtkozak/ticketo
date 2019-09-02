package com.google.ticketo.database.Local.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.ticketo.model.Event
import java.util.*

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAllEvents(): LiveData<Event>

    @Query("SELECT * FROM event WHERE city= :city")
    fun getEventsInCity(city: String): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE city= :city AND lastUpdate> :lastUpdate LIMIT 1")
    fun checkUpdateWithCity(city: String, lastUpdate: Date) : Event?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events : List<Event>)

}