package com.google.ticketo.database.Local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.ticketo.database.Local.Daos.Converters.DateConverter
import com.google.ticketo.database.Local.Daos.EventDao
import com.google.ticketo.database.Local.Daos.EventIntentsDao
import com.google.ticketo.database.Local.Daos.LocationDao
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.Event
import com.google.ticketo.model.EventDto
import com.google.ticketo.model.EventIntents
import com.google.ticketo.model.Location

@Database(entities = arrayOf(EventDto::class, EventIntents::class, Location::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun eventDao(): EventDao
    abstract fun eventIntentsDao() : EventIntentsDao
    abstract fun locationDao() : LocationDao

    companion object {
        private var instance: LocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java, "LocalDatabase"
                ).build()
            return instance as LocalDatabase
        }
    }


}