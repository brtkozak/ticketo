package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.ticketo.model.User
import io.reactivex.Single

fun DocumentReference.single(): Single<DocumentSnapshot> =
    Single.create { emitter ->
        this.get()
            .addOnSuccessListener { it ->
                emitter.onSuccess(it)
            }
            .addOnFailureListener {

            }
    }

fun Query.single(): Single<QuerySnapshot> =
    Single.create { emitter ->
        this.get()
            .addOnSuccessListener { it ->
                emitter.onSuccess(it)
            }
            .addOnFailureListener {

            }

    }

fun <T> Task<T>.single(): Single<Boolean> =
    Single.create { emitter ->
        this.addOnSuccessListener {
            emitter.onSuccess(true)
        }
            .addOnFailureListener {

            }
    }

