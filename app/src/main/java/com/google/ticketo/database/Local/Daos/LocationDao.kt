package com.google.ticketo.database.Local.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.google.ticketo.model.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocations(locations : List<Location>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location : Location)
}