package com.google.ticketo.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import com.google.ticketo.model.Event

class FavouritesViewModel(repository: Repository) : ViewModel() {

    val favouriteEvents : LiveData<List<Event>> =  repository.getFavouriteEvents()


}
