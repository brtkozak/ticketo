package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.model.*
import com.google.ticketo.model.Responses.eventResponse.EventResponse
import io.reactivex.Single
import java.util.*

//SINGLETON
class FirestoreRepository {

    private val database = FirebaseFirestore.getInstance()
    val fireAuth = FirebaseAuth.getInstance()

    companion object {
        private var instance: FirestoreRepository? = null

        @Synchronized
        fun getInstance(): FirestoreRepository {
            if (instance == null)
                instance =
                    FirestoreRepository()
            return instance as FirestoreRepository
        }
    }

    fun getCurrentUser(): Single<User> =
        database
            .collection("users")
            .document(fireAuth.uid!!)
            .single()
            .map {
                it.toObject(User::class.java)!!
            }

    fun getAllEvents(): Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }

    fun getEvent(id : String) : Single<Pair<EventDto?, Location?>> =
        database.collection("events")
            .whereEqualTo("id", id)
            .single()
            .map { it ->
                DtoConverter.querySnapshotToEvent(it)
            }

    fun getEventsByCity(city: String): Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .whereEqualTo("location.city", city)
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }

    fun getEventsByDates(
        startDate: Date,
        endDate: Date
    ): Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .whereGreaterThanOrEqualTo("startDate", startDate)
            .whereLessThanOrEqualTo("startDate", endDate)
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }


    fun getBuyersCount(eventId: String): Single<Int> =
        database.collection("events")
            .document(eventId)
            .collection("buyers")
            .single()
            .map {
                it.size()
            }

    fun getSellersCount(eventId: String): Single<Int> =
        database.collection("events")
            .document(eventId)
            .collection("sellers")
            .single()
            .map {
                it.size()
            }

    fun getGroup(eventId: String, group: String): LiveData<List<User>> =
        UserListLiveData(
            database.collection("events")
                .document(eventId)
                .collection(group)
        )

    fun getUser(userId : String) : LiveData<User> =
        UserLiveData(
            database.collection("users")
                .document(userId)
        )

    fun addToGroup(user: User, eventId: String, group: String): Single<Boolean> =
        database.collection("events")
            .document(eventId)
            .collection(group)
            .document(user.firebaseId!!).set(user)
            .single()

    fun removeFromGroup(eventId: String, group: String): Single<Boolean> =
        database.collection("events")
            .document(eventId)
            .collection(group)
            .document(fireAuth.uid!!)
            .delete()
            .single()

    fun checkIfUserInGroup(eventId: String, group: String): Single<Boolean> =
        database.collection("events")
            .document(eventId)
            .collection(group)
            .document(fireAuth.uid!!)
            .single()
            .map {
                it.exists()
            }

    fun getSearchByName(search: String) : Single<List<Pair<String, String>>>{
        return database.collection("events")
            .orderBy("name")
            .startAt(search.capitalize())
            .endAt(search.capitalize() + "\uf8ff")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsNameList(it)
            }
    }

    fun getSearchByLocation(search: String) : Single<List<Pair<String, String>>>{
        return database.collection("events")
            .orderBy("location.city")
            .startAt(search.capitalize())
            .endAt(search.capitalize() + "\uf8ff")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsLocationList(it)
            }
    }

    fun insertEvent() {
        val events = mutableListOf<EventResponse>()

        val event1 = EventResponse(
            "442909573185318",
            "Dawid Podsiadło i Taco Hemingway na PGE Narodowym",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/61306347_2479059405462027_4471323510781771776_o.jpg?_nc_cat=102&_nc_oc=AQkwpSSJsxkMDlseCZ1z6VqK1JAiba3mgSUfs09ngplhNp8-__x8ZBzg6hqGujzOejc&_nc_ht=scontent-waw1-1.xx&oh=a80cd940d33db06933ba636fc88678a7&oe=5E008A1A",
            Date(1569690000),
            Date(1569706200),
            Location("PGE Narodowy", "Warszawa", "al. Ks. J. Poniatowskiego 1", "03-901"),
            "PLN",
            49.0,
            399.0
        )

        val event2 = EventResponse(
            "336484410469514",
            "Michael Bublé",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/51556332_805938009744499_1722003395721560064_o.jpg?_nc_cat=107&_nc_oc=AQmGtJQLXghqTpriDF8nleDt0wFeoRHjyPu7v0K2tK0zxuYBtKGD3bABvcnmL9-X6lk&_nc_ht=scontent-waw1-1.xx&oh=e2880f317ba1a63b8cfef61d428b61fd&oe=5E043447",
            Date(1568916000),
            Date(1568925000),
            Location("Atlas Arena", "Łódź", "al. Bandurskiego 7", "94-020"),
            "PLN",
            -1.0,
            -1.0
        )

        val event3 = EventResponse(
            "445836486216356",
            "KęKę - Mr KęKę",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/65580872_2420692177994794_4925162822230343680_o.jpg?_nc_cat=111&_nc_oc=AQlG-gxNsGDPs2dYzRcY7iwGIDOD3m0WeB5MEAB72D2_TJvB_ALE2D6VVnc-KknBVXA&_nc_ht=scontent-waw1-1.xx&oh=9eb800ddfdc7972dda0aff4bc2485713&oe=5E03459E",
            Date(1569096000),
            Date(1569103140),
            Location("Akademia CLUB", "Wrocław", "ul. Grunwaldzka 67", "50-357"),
            "PLN",
            -1.0,
            -1.0
        )

        events.addAll(listOf(event1, event2, event3))

        events.forEach {
            database.collection("events").document(it.id).set(it)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                }
        }
    }
}