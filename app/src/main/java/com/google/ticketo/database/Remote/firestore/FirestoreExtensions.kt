package com.google.ticketo.database.Remote.firestore

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
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

fun Query.single() : Single<QuerySnapshot> =
       Single.create{ emitter ->
           this.get()
               .addOnSuccessListener { it ->
                   emitter.onSuccess(it)
               }
               .addOnFailureListener {

               }

       }