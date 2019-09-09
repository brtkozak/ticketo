package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.model.*
import io.reactivex.Single
import java.util.*

//SINGLETON
class FirestoreRepository {

    private val database = FirebaseFirestore.getInstance()
    private val fireAuth = FirebaseAuth.getInstance()

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

    fun getAllEvents(): Single<List<Event>> =
        database.collection("events")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }

    fun getEventsByCity(city: String): Single<List<Event>> =
        database.collection("events")
            .whereEqualTo("location.city", city)
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }

    fun getEventsByDates(startDate: Date, endDate: Date): Single<List<Event>> =
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

    fun addToBuyers(user : User, eventId: String) : Single <Boolean>{
        database.collection("events")
            .document(eventId)
            .collection("buyers")
            .document(user.firebaseId!!).set(user)
            .addOnSuccessListener {

            }
    }


    fun insertEvent() {
        val events = mutableListOf<Event>()

        val event1 = Event(
            "442909573185318",
            "Dawid Podsiadło i Taco Hemingway na PGE Narodowym",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/61306347_2479059405462027_4471323510781771776_o.jpg?_nc_cat=102&_nc_oc=AQkwpSSJsxkMDlseCZ1z6VqK1JAiba3mgSUfs09ngplhNp8-__x8ZBzg6hqGujzOejc&_nc_ht=scontent-waw1-1.xx&oh=a80cd940d33db06933ba636fc88678a7&oe=5E008A1A",
            Date(1569690000),
            Date(1569706200),
            Location("PGE Narodowy", "Warszawa", "al. Ks. J. Poniatowskiego 1", "03-901"),
            Price(49, 399, "PLN")
        )

        val event2 = Event(
            "336484410469514",
            "Michael Bublé",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/51556332_805938009744499_1722003395721560064_o.jpg?_nc_cat=107&_nc_oc=AQmGtJQLXghqTpriDF8nleDt0wFeoRHjyPu7v0K2tK0zxuYBtKGD3bABvcnmL9-X6lk&_nc_ht=scontent-waw1-1.xx&oh=e2880f317ba1a63b8cfef61d428b61fd&oe=5E043447",
            Date(1568916000),
            Date(1568925000),
            Location("Atlas Arena", "Łódź", "al. Bandurskiego 7", "94-020"),
            Price(-1, -1, "PLN")
        )

        val event3 = Event(
            "445836486216356",
            "KęKę - Mr KęKę",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/65580872_2420692177994794_4925162822230343680_o.jpg?_nc_cat=111&_nc_oc=AQlG-gxNsGDPs2dYzRcY7iwGIDOD3m0WeB5MEAB72D2_TJvB_ALE2D6VVnc-KknBVXA&_nc_ht=scontent-waw1-1.xx&oh=9eb800ddfdc7972dda0aff4bc2485713&oe=5E03459E",
            Date(1569096000),
            Date(1569103140),
            Location("Akademia CLUB", "Wrocław", "ul. Grunwaldzka 67", "50-357"),
            Price(40, -1, "PLN")
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