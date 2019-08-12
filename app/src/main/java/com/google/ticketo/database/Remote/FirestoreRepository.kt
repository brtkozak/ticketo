package com.google.ticketo.database.Remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.model.User
import com.google.ticketo.ui.profile.CurrentUserCallback
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


///SINGLETON
class FirestoreRepository {


    private val database = FirebaseFirestore.getInstance()
    private val fireAuth = FirebaseAuth.getInstance()
    lateinit var callback: CurrentUserCallback

    companion object {
        private var instance: FirestoreRepository? = null

        fun getInstance(): FirestoreRepository {
            if (instance == null)
                instance = FirestoreRepository()
            return instance as FirestoreRepository
        }
    }

    fun getCurrentUser() {
        val query = database.collection("users").document(fireAuth.uid!!)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.toObject(User::class.java)
                callback.updateUser(user)
            } else {

            }
        }
    }

}