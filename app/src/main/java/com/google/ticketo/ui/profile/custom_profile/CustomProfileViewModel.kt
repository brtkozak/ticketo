package com.google.ticketo.ui.profile.custom_profile

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Review
import com.google.ticketo.model.User
import com.google.ticketo.utils.Const
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.wait

class CustomProfileViewModel(val repository: Repository, val userId: String) : ViewModel() {

    val user: LiveData<User> = repository.getUser(userId)
    val recommendations: LiveData<List<Review>> = repository.getReviews(userId, Const.RECOMMEND)
    val warns: LiveData<List<Review>> = repository.getReviews(userId, Const.WARN)

    val _recommendationLock = MutableLiveData<Int>(0)
    val recommendationLock: LiveData<Int> = _recommendationLock

    val _warnLock = MutableLiveData<Int>(0)
    val warnLock: LiveData<Int> = _warnLock

    val currentUser = repository.firestoreRepository.fireAuth.uid

    @SuppressLint("CheckResult")
    fun addReview(reviewType: String) {
        repository.addReview(userId, reviewType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (reviewType == Const.RECOMMEND)
                    _recommendationLock.value = _recommendationLock.value?.minus(1)
                else
                    _warnLock.value = _warnLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun removeReview(toRemoveType: String, contextType : String) {
        repository.removeReview(userId, toRemoveType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (contextType == Const.RECOMMEND)
                    _recommendationLock.value = _recommendationLock.value?.minus(1)
                else
                    _warnLock.value = _warnLock.value?.minus(1)
            }
    }

}
