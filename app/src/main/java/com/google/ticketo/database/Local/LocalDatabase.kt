package com.google.ticketo.database.Local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.ticketo.database.Local.Daos.Converters.DateConverter
import com.google.ticketo.database.Local.Daos.EventDao
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.Event

@Database(entities = arrayOf(Event::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun eventDao(): EventDao

    companion object {
        private var instance: LocalDatabase? = null

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