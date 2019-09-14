package com.google.ticketo.database.Local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.ticketo.database.Local.Daos.Converters.DateConverter
import com.google.ticketo.database.Local.Daos.EventDao
import com.google.ticketo.database.Local.Daos.EventIntentsDao
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.Event
import com.google.ticketo.model.EventIntents

@Database(entities = arrayOf(Event::class, EventIntents::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun eventDao(): EventDao
    abstract fun eventIntentsDao() : EventIntentsDao

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