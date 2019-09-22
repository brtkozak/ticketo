package com.google.ticketo.ui.event_details

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import com.google.ticketo.model.EventIntents
import com.google.ticketo.model.User
import com.google.ticketo.utils.Const
import com.google.ticketo.utils.Const.BUYERS
import com.google.ticketo.utils.Const.BUY_INTENT
import com.google.ticketo.utils.Const.SELLERS
import com.google.ticketo.utils.Const.SELL_INTENT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventDetailsViewModel(private val repository: Repository,val eventId : String) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    val _buyLock = MutableLiveData<Int>(0)
    val buyLock: LiveData<Int> = _buyLock

    val _sellLock = MutableLiveData<Int>(0)
    val sellLock: LiveData<Int> = _sellLock

    val _layoutReady = MutableLiveData<Int>(0)  // TODO USE TO LOAD REST OF EVENT DATA
    val layoutReady: LiveData<Int> = _layoutReady

    val eventIntents : LiveData<EventIntents> = repository.getEventIntents(eventId)

    val buyers : LiveData<List<User>> = repository.getGroup(eventId, BUYERS)

    val sellers : LiveData<List<User>> = repository.getGroup(eventId, SELLERS)

    @SuppressLint("CheckResult")
    fun setEvent() {
        repository.getEvent(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _event.value = it
            }
    }

//    fun getBuyers(): LiveData<List<User>> =
//        repository.getGroup(eventId, BUYERS)

//    fun getSellers(): LiveData<List<User>> =
//        repository.getGroup(eventId, SELLERS)

    @SuppressLint("CheckResult")
    fun addToBuyers() {
        repository.addToGroup(eventId, BUYERS, BUY_INTENT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _buyLock.value = _buyLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun removeFromBuyers(click: String) {
        repository.removeFromGroup(eventId, BUYERS, BUY_INTENT)
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
        repository.addToGroup(eventId!!, SELLERS, SELL_INTENT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                _sellLock.value = _sellLock.value?.minus(1)
            }
    }

    @SuppressLint("CheckResult")
    fun removeFromSellers(click: String) {
        repository.removeFromGroup(eventId, SELLERS, SELL_INTENT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                if (click == Const.BUYERS)
                    _buyLock.value = _buyLock.value?.minus(1)
                else
                    _sellLock.value = _sellLock.value?.minus(1)
            }
    }

    fun addToFavourites(){
        updateFavourites(true)
    }

    fun removeFromFavourites(){
        updateFavourites(false)
    }

    fun updateFavourites(state : Boolean){
        repository.updateFavourites(eventId, state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}
