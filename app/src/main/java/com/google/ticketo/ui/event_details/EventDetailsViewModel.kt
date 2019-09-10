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
    val event = _event

//    private val _buyers = MutableLiveData<Int>()
//    val buyers: LiveData<Int> = _buyers
//
//    private val _sellers = MutableLiveData<Int>()
//    val sellers: LiveData<Int> = _sellers

    private val BUYERS = "buyers"
    private val SELLERS = "sellers"

    @SuppressLint("CheckResult")
    fun setEvent(eventId: String) {
        repository.getEvent(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _event.value = it
            }

//        setBuyers(eventId)
//        setSellers(eventId)
    }

    fun getBuyers(): LiveData<List<User>> =
        repository.getGroup(eventId!!, BUYERS)


//    @SuppressLint("CheckResult")
//    private fun setBuyers(eventId: String) {
//        repository.getBuyersCount(eventId)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe { it ->
//                _buyers.value = it
//            }
//    }
//
//    @SuppressLint("CheckResult")
//    private fun setSellers(eventId: String) {
//        repository.getSellersCount(eventId)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe { it ->
//                _sellers.value = it
//            }
//    }


    fun addToBuyers() {
        repository.addToGroup(eventId!!, BUYERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun removeFromBuyers() {
        repository.removeFromGroup(eventId!!, BUYERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun addToSellers() {
        repository.addToGroup(eventId!!, SELLERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun removeFromSellers() {
        repository.removeFromGroup(eventId!!, SELLERS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
