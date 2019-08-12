package com.google.ticketo.database.Remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.ticketo.database.Remote.FacebookApi
import com.google.ticketo.model.Responses.UserResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FacebookRepository {

    companion object {

        private val facebookApi = FacebookApi.create()
        private val fireAuth = FirebaseAuth.getInstance()

        fun getCurrentUserData(id: String = fireAuth.currentUser!!.uid): Observable<UserResponse> {
            return facebookApi.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
        }
    }

}