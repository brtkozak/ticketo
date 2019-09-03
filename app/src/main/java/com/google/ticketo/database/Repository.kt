package com.google.ticketo.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.firebase.database.core.Repo
import com.google.ticketo.database.Local.LocalDatabase
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.Event
import io.reactivex.schedulers.Schedulers
import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Repository(context: Context) {
    private val firestoreRepository = FirestoreRepository.getInstance()
    private val executor = Executors.newSingleThreadExecutor()
    val localDatabase = LocalDatabase.getInstance(context)
    val eventDao = localDatabase.eventDao()

    companion object {
        private var instance: Repository? = null

        fun getInstance(context: Context): Repository {
            if (Repository.instance == null)
                Repository.instance =
                    Repository(context)
            return Repository.instance as Repository
        }
    }

    fun getEventsInCity(city: String): LiveData<List<Event>> {
        updateGetEventsInCity(city)
        return eventDao.getEventsInCity(city)
    }

    private val eventsTimeout = 2
    private fun updateGetEventsInCity(city: String) {
        executor.execute {
            val exist =
                (eventDao.checkUpdateWithCity(city, getUpdateTime(Date(), eventsTimeout)) != null)
            if (!exist) {
                Log.d("looog", "from remote")
                firestoreRepository
                    .getEventsByCity(city)
                    .subscribe { it ->
                        Log.d("looog", "" + it.size)
                        it.forEach {
                            it.lastUpdate = Date()
                        }
                        executor.execute {
                            Log.d("looog", "saving")
                            eventDao.insertEvents(it)
                        }
                    }
            } else
                Log.d("looog", "from local")
        }
    }

    fun getEventsThisWeekend(): LiveData<List<Event>> {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var startDate = Date()
        val endDate = getClosestDay(DayOfWeek.SUNDAY, 1)
        if (day != Calendar.FRIDAY || day != Calendar.SATURDAY || day != Calendar.SUNDAY) {
            startDate = getClosestDay(DayOfWeek.FRIDAY)
        }
        Log.d(startDate.toString(), endDate.toString())
        updateGetEventsByDates(startDate, endDate)
        return eventDao.getEventsThisWeekend(startDate, endDate)
    }

    private fun updateGetEventsByDates(startDate: Date, endDate: Date) {
        executor.execute {
            val exist =
                (eventDao.checkUpdateWithDates(startDate, endDate, getUpdateTime(Date(), eventsTimeout)) != null)
            if (!exist) {
                Log.d("looog", "from remote")

                firestoreRepository
                    .getEventsByDates(startDate, endDate)
                    .subscribe { it ->
                        it.forEach {
                            it.lastUpdate = Date()
                        }
                        executor.execute {
                            Log.d("looog", "saving")
                            eventDao.insertEvents(it)
                        }
                    }
            } else
                Log.d("looog", "from local")
        }
    }

    private fun getClosestDay(day: DayOfWeek, plusDays: Long = 0): Date {
        val date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day)).plusDays(plusDays)
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    private fun getUpdateTime(date: Date, minutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MINUTE, -minutes)
        return cal.time
    }

}