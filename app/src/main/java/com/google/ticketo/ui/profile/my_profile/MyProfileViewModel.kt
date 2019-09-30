package com.google.ticketo.ui.profile.my_profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Review
import com.google.ticketo.model.User
import com.google.ticketo.utils.Const
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MyProfileViewModel(val repository : Repository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _loading = MutableLiveData<Boolean> (true)
    val loading : LiveData<Boolean> = _loading

    val buyCounter : LiveData<Int> = repository.getEventsWithBuyIntentCount()
    val sellCounter : LiveData<Int> = repository.getEventsWithSellIntentCount()

    val recommendations : LiveData<List<Review>> = repository.getReviews(repository.firestoreRepository.fireAuth.uid!!, Const.RECOMMEND)
    val warnings : LiveData<List<Review>> = repository.getReviews(repository.firestoreRepository.fireAuth.uid!!, Const.WARN)

    init {
        getUser()
    }

    @SuppressLint("CheckResult")
    private fun getUser() {
        repository.getCurrentUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _user.value=it
                _loading.value=false
            }
    }
}
