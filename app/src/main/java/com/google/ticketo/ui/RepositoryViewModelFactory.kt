package com.google.ticketo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.database.Repository

class RepositoryViewModelFactory(val repository : Repository)  : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}