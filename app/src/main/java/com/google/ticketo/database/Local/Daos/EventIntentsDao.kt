package com.google.ticketo.database.Local.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.ticketo.model.Event
import com.google.ticketo.model.EventIntents

@Dao
interface EventIntentsDao {

    @Query("SELECT * FROM eventintents WHERE eventId= :eventId")
    fun getEventIntents(eventId : String) : LiveData<EventIntents>

    @Query("SELECT * FROM eventintents WHERE eventId= :eventId LIMIT 1")
    fun checkEventIntents (eventId : String) : EventIntents?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertEventsIntents(eventIntents : List<EventIntents>)

    @Query("UPDATE eventintents SET buy = :state WHERE eventId = :eventId")
    fun updateBuyIntent(eventId: String, state: Boolean)

    @Query("UPDATE eventintents SET sell = :state WHERE eventId = :eventId")
    fun updateSellIntent(eventId: String, state: Boolean)

    @Query("UPDATE eventintents SET favourite = :state WHERE eventId = :eventId")
    fun updateFavourites(eventId : String, state : Boolean) : Int

    @Query("SELECT eventdto.* FROM eventdto JOIN eventintents ON eventdto.id = eventintents.eventId WHERE favourite= 1")
    fun getFavouriteEvents() : LiveData<List<Event>>
}