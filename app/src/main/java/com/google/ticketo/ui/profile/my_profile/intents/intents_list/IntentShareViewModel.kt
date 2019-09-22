package com.google.ticketo.ui.profile.my_profile.intents.intents_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import com.google.ticketo.model.EventWithIntents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IntentShareViewModel(val repository : Repository) : ViewModel() {

    val buyEvents : LiveData<List<EventWithIntents>> = repository.getEventsWithBuyIntent()
    val sellEvents : LiveData<List<EventWithIntents>> = repository.getEventsWithSellIntent()

    fun updateFavourites(eventId : String, state : Boolean){
        repository.updateFavourites(eventId, state)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
