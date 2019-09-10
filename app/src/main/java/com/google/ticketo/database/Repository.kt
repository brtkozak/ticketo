package com.google.ticketo.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.firebase.database.core.Repo
import com.google.ticketo.database.Local.LocalDatabase
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.Event
import com.google.ticketo.model.User
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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
    val firestoreRepository = FirestoreRepository.getInstance()
    private val executor = Executors.newSingleThreadExecutor()
    val localDatabase = LocalDatabase.getInstance(context)
    private val eventDao = localDatabase.eventDao()

    companion object {
        private var instance: Repository? = null

        @Synchronized
        fun getInstance(context: Context): Repository {
            if (instance == null)
                instance =
                    Repository(context)
            return instance as Repository
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
        if (day != Calendar.FRIDAY && day != Calendar.SATURDAY && day != Calendar.SUNDAY) {
            startDate = getClosestDay(DayOfWeek.FRIDAY)
        }
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

    fun getEvent(eventId: String): Single<Event> {
        return Single.fromCallable { eventDao.getEvent(eventId) }
    }

    fun getBuyersCount(eventId: String): Single<Int> =
        firestoreRepository.getBuyersCount(eventId)

    fun getSellersCount(eventId: String): Single<Int> =
        firestoreRepository.getSellersCount(eventId)

    fun getGroup(eventId: String, group : String) : LiveData<List<User>> =
            firestoreRepository.getGroup(eventId, group)

    fun addToGroup(eventId: String, group : String): Single<Boolean> =
        firestoreRepository.getCurrentUser().flatMap {
            firestoreRepository.addToGroup(it, eventId, group)
        }


    fun removeFromGroup(eventId: String, group : String): Single<Boolean> =
        firestoreRepository.getCurrentUser().flatMap {
            firestoreRepository.removeFromGroup(it.firebaseId!!, eventId, group)
        }

}