package com.google.ticketo.ui.event_details

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import com.google.ticketo.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventDetailsViewModel(private val repository: Repository) : ViewModel() {

    var eventId: String? = null

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _initState = MutableLiveData<String>()
    val initState: LiveData<String> = _initState

    val _buyLock = MutableLiveData<Int>(0)
    val buyLock: LiveData<Int> = _buyLock

    val _sellLock = MutableLiveData<Int>(0)
    val sellLock: LiveData<Int> = _sellLock

    val _layoutReady = MutableLiveData<Int>(2)
    val layoutReady: LiveData<Int> = _layoutReady

    val BUYERS = "buyers"
    val SELLERS = "sellers"

    @SuppressLint("CheckResult")
    fun setEvent(eventId: String) {
        repository.getEvent(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _event.value = it
                checkIfUserInBuyers()
                checkIfUserInSellers()
            }
    }

    fun getBuyers(): LiveData<List<User>> =
        repository.getGroup(eventId!!, BUYERS)

    fun getSellers(): LiveData<List<User>> =
        repository.getGroup(eventId!!, SELLERS)

    @SuppressLint("CheckResult")
    fun addToBuyers() {
        repository.addToGroup(eventId!!, BUYERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _buyLock.value = _buyLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun removeFromBuyers(click: String) {
        repository.removeFromGroup(eventId!!, BUYERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (click == BUYERS)
                    _buyLock.value = _buyLock.value?.minus(1)
                else
                    _sellLock.value = _sellLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun addToSellers() {
        repository.addToGroup(eventId!!, SELLERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _sellLock.value = _sellLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun removeFromSellers(click: String) {
        repository.removeFromGroup(eventId!!, SELLERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (click == BUYERS)
                    _buyLock.value = _buyLock.value?.minus(1)
                else
                    _sellLock.value = _sellLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun checkIfUserInBuyers() {
        repository.checkIfUserInGroup(eventId!!, BUYERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (it)
                    _initState.value = BUYERS
                _layoutReady.value = _layoutReady.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun checkIfUserInSellers() {
        repository.checkIfUserInGroup(eventId!!, SELLERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (it)
                    _initState.value = SELLERS
                _layoutReady.value = _layoutReady.value?.minus(1)

            }
    }


}
