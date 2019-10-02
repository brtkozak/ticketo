package com.google.ticketo.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.database.Repository

class StringViewModelFactory(val context : Context, val string : String)  : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  modelClass.getConstructor(Repository::class.java, String::class.java).newInstance(
            Repository.getInstance(context), string) as T
    }
}