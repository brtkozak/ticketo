package com.google.ticketo.database

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.ticketo.database.Local.LocalDatabase
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.*
import com.google.ticketo.utils.Const.BUY_INTENT
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.*
import java.util.concurrent.Executors

class Repository(context: Context) {
    val firestoreRepository = FirestoreRepository.getInstance()
    private val executor = Executors.newSingleThreadExecutor()
    private val localDatabase = LocalDatabase.getInstance(context)

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

    fun getEventsInCity(city: String): LiveData<List<EventInfo>> {
        updateGetEventsInCity(city)
        return localDatabase.eventDao().getEventsInCity(city)
    }

    private val eventsTimeout = 2
    private fun updateGetEventsInCity(city: String) {
        executor.execute {
            val exist =
                (localDatabase.eventDao().checkUpdateWithCity(
                    city,
                    getUpdateTime(Date(), eventsTimeout)
                ) != null)
            if (!exist) {
                Log.d("looog", "from remote")
                firestoreRepository
                    .getEventsByCity(city)
                    .subscribe { it ->

                        executor.execute {
                            localDatabase.locationDao().insertLocations(it.second)
                        }

                        executor.execute {
                            Log.d("looog", "saving")
                            localDatabase.eventDao().insertEvents(it.first)
                        }

                        executor.execute {
                            val eventIntents = mutableListOf<EventIntents>()
                            it.first.forEach {
                                if (localDatabase.eventIntentsDao().checkEventIntents(it.id) == null)
                                    eventIntents.add(EventIntents(eventId = it.id))
                            }
                            localDatabase.eventIntentsDao().insertEventsIntents(eventIntents)
                        }

                    }
            } else
                Log.d("looog", "from local")
        }
    }

    fun getEventsWithIntentsByCity(city: String): LiveData<List<Event>> {
        updateGetEventsInCity(city)
        return localDatabase.eventDao().getEventWithIntentsByCity(city)
    }

    fun getEventWithIntents(eventId: String): LiveData<Event> {
        return localDatabase.eventDao().getEventWithIntents(eventId)
    }

    fun getEventsThisWeekend(): LiveData<List<EventInfo>> {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var startDate = Date()
        val endDate = getClosestDay(DayOfWeek.SUNDAY, 1)
        if (day != Calendar.FRIDAY && day != Calendar.SATURDAY && day != Calendar.SUNDAY) {
            startDate = getClosestDay(DayOfWeek.FRIDAY)
        }
        updateGetEventsByDates(startDate, endDate)
        return localDatabase.eventDao().getEventsThisWeekend(startDate, endDate)
    }

    private fun updateGetEventsByDates(startDate: Date, endDate: Date) {
        executor.execute {
            val exist =
                (localDatabase.eventDao().checkUpdateWithDates(
                    startDate,
                    endDate,
                    getUpdateTime(Date(), eventsTimeout)
                ) != null)
            if (!exist) {
                Log.d("looog", "from remote")

                firestoreRepository
                    .getEventsByDates(startDate, endDate)
                    .subscribe { it ->
                        executor.execute {
                            localDatabase.locationDao().insertLocations(it.second)
                        }
                        executor.execute {
                            Log.d("looog", "saving")
                            localDatabase.eventDao().insertEvents(it.first)
                        }

                        executor.execute {
                            val eventIntents = mutableListOf<EventIntents>()
                            it.first.forEach {
                                if (localDatabase.eventIntentsDao().checkEventIntents(it.id) == null)
                                    eventIntents.add(EventIntents(eventId = it.id))
                            }
                            localDatabase.eventIntentsDao().insertEventsIntents(eventIntents)
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

    fun getEvent(eventId: String): Single<EventInfo> {
        updateGetEvent(eventId)
        return Single.fromCallable { localDatabase.eventDao().getEvent(eventId) }
    }

    private fun updateGetEvent(eventId: String) {
        executor.execute {
            val exist =
                (localDatabase.eventDao().checkEventUpdate(
                    eventId,
                    getUpdateTime(Date(), eventsTimeout)
                ) != null)
            if (!exist) {
                Log.d("looog", "from remote")

                firestoreRepository
                    .getEvent(eventId)
                    .subscribe { it ->
                        executor.execute {
                            it.second?.let { location ->
                                localDatabase.locationDao().insertLocation(location)
                            }
                        }
                        executor.execute {
                            Log.d("looog", "saving")
                            it.first?.let { event -> localDatabase.eventDao().insertEvent(event) }
                        }

                        executor.execute {
                            val eventIntents = mutableListOf<EventIntents>()
                            if (it.first != null)
                                eventIntents.add(EventIntents(eventId = it.first!!.id))
                            localDatabase.eventIntentsDao().insertEventsIntents(eventIntents)
                        }
                    }
            } else
                Log.d("looog", "from local")
        }
    }

    fun getGroup(eventId: String, group: String): LiveData<List<User>> =
        firestoreRepository.getGroup(eventId, group)

    fun addToGroup(eventId: String, group: String, intent: String): Single<Boolean> {
        if (intent == BUY_INTENT)
            Single.fromCallable {
                localDatabase.eventIntentsDao().updateBuyIntent(eventId, true)
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()
        else
            Single.fromCallable { localDatabase.eventIntentsDao().updateSellIntent(eventId, true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()

        return firestoreRepository.getCurrentUser().flatMap {
            firestoreRepository.addToGroup(it, eventId, group)
        }
    }

    fun removeFromGroup(eventId: String, group: String, intent: String): Single<Boolean> {
        if (intent == BUY_INTENT)
            Single.fromCallable { localDatabase.eventIntentsDao().updateBuyIntent(eventId, false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()
        else
            Single.fromCallable { localDatabase.eventIntentsDao().updateSellIntent(eventId, false) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()


        return firestoreRepository.removeFromGroup(eventId, group)
    }

    fun updateFavourites(eventId: String, state: Boolean): Single<Int> {
        return Single.fromCallable {
            localDatabase.eventIntentsDao().updateFavourites(eventId, state)
        }
    }

    fun getCurrentUser(): Single<User> =
        firestoreRepository.getCurrentUser()

    fun getEventIntents(eventId: String): LiveData<EventIntents> =
        localDatabase.eventIntentsDao().getEventIntents(eventId)

    fun getFavouriteEvents(): LiveData<List<Event>> =
        localDatabase.eventIntentsDao().getFavouriteEvents()

    fun getSearchByName(search: String): Single<List<Pair<String, String>>> =
        firestoreRepository.getSearchByName(search)

    fun getSearchByLocation(search: String): Single<List<Pair<String, String>>> =
        firestoreRepository.getSearchByLocation(search)

    fun getEventsWithBuyIntent(): LiveData<List<Event>> =
        localDatabase.eventDao().getEventsWithBuyIntent()

    fun getEventsWithSellIntent(): LiveData<List<Event>> =
        localDatabase.eventDao().getEventsWithSellIntent()

    fun getEventsWithBuyIntentCount(): LiveData<Int> =
        localDatabase.eventDao().getEventsWithBuyIntentCount()

    fun getEventsWithSellIntentCount(): LiveData<Int> =
        localDatabase.eventDao().getEventsWithSellIntentCount()

    fun getUser(userId: String): LiveData<User> =
        firestoreRepository.getUser(userId)

    fun getReviews(userId: String, reviewType: String) =
        firestoreRepository.getReviews(userId, reviewType)

    fun addReview(userId: String, reviewType: String) =
        firestoreRepository.addReview(userId, reviewType)

    fun removeReview(userId: String, reviewType: String) =
        firestoreRepository.removeReview(userId, reviewType)

    @SuppressLint("CheckResult")
    fun sendComment(comment: String, eventId: String): Single<Boolean> =
        firestoreRepository.getCurrentUser().flatMap {
            val commentDto = CommentDto(
                comment,
                it.firebaseId,
                it.name,
                it.picture
            )
            firestoreRepository.sendComment(commentDto, eventId)
    }

    fun getComments(eventId: String) : LiveData<List<Comment>> =
        firestoreRepository.getComments(eventId)

    fun getUserPic() : Single<String> =
        firestoreRepository.getCurrentUser().flatMap {
            Single.just(it.picture)
        }

    fun deleteComment(eventId : String, commentId : String) : Single<Boolean> =
        firestoreRepository.deleteComment(eventId, commentId)
}

