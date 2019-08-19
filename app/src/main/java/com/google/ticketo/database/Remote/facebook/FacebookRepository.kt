package com.google.ticketo.database.Remote.facebook

import com.google.firebase.auth.FirebaseAuth
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.Responses.userResponse.UserResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FacebookRepository {

    private val facebookApi = FacebookApi.create()
    private val fireAuth = FirebaseAuth.getInstance()

    companion object {
        private var instance: FacebookRepository? = null

        fun getInstance(): FacebookRepository {
            if (instance == null)
                instance =
                    FacebookRepository()
            return instance as FacebookRepository
        }
    }

    fun getCurrentUserData(id: String = fireAuth.currentUser!!.uid): Observable<UserResponse> {
        return facebookApi.getUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
    }
}