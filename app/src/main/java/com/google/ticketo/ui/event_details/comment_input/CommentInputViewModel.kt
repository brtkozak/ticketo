package com.google.ticketo.ui.event_details.comment_input

import androidx.lifecycle.ViewModel
import com.google.ticketo.database.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentInputViewModel(val repository: Repository, val eventId : String) : ViewModel() {

    fun sendComment(comment : String){
        repository.sendComment(comment, eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
