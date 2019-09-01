package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.model.DtoConverter
import com.google.ticketo.model.Event
import com.google.ticketo.model.User
import io.reactivex.Single


///SINGLETON
class FirestoreRepository {

    private val database = FirebaseFirestore.getInstance()
    private val fireAuth = FirebaseAuth.getInstance()

    companion object {
        private var instance: FirestoreRepository? = null

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
                it.toObject(User::class.java)
            }

    fun getAllEvents(): Single<List<Event>> =
        database.collection("events")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }


    fun insertEvent() {
        val events = mutableListOf<Event>()

        val event1 = Event(

        )

        events.forEach {
            database.collection("events")
                .add(it)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                }
        }
    }
}