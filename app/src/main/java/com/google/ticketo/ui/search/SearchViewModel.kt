package com.google.ticketo.ui.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.ticketo.database.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(val repository : Repository) : ViewModel() {

    private val _names = MutableLiveData<List<String>>()
    val names : LiveData<List<String>> = _names

    private val _locations = MutableLiveData<List<String>>()
    val locations : LiveData<List<String>> = _locations

    @SuppressLint("CheckResult")
    fun search(search : String) {
        repository.getSearchByName(search)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{ it ->
                _names.value=it
            }

        repository.getSearchByLocation(search)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{ it ->
                _locations.value=it
            }
    }


}
