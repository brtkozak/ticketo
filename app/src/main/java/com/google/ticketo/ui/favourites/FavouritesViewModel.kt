package com.google.ticketo.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavouritesViewModel(private val repository: Repository) : ViewModel() {

    val favouriteEvents : LiveData<List<Event>> =  repository.getFavouriteEvents()

    fun removeFromFavourites(eventId : String){
        repository.updateFavourites(eventId, false)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
