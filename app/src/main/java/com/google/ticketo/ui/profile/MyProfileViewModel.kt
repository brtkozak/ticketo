package com.google.ticketo.ui.profile

import android.util.Log
import androidx.lifecycle.*
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.model.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MyProfileViewModel : ViewModel() {

    private val firestore = FirestoreRepository.getInstance()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _loading = MutableLiveData<Boolean> (true)
    val loading : LiveData<Boolean> = _loading

    init {
        getUser()
    }

    private fun getUser() {
       firestore.getCurrentUser()
           .subscribe { it ->
               _user.value=it
               _loading.value=false
           }
    }
}
