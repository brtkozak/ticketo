package com.google.ticketo.ui.profile.custom_profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.database.Repository

class CustomProfileFactory(val context : Context, val userId : String)  : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CustomProfileViewModel(Repository.getInstance(context), userId) as T
    }
}