package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.google.ticketo.model.DtoConverter
import com.google.ticketo.model.User

class UserLiveData(private val documentReference: DocumentReference) : LiveData<User>() {

    private val listener = MySnapshotListener()

    override fun onActive() {
        documentReference.addSnapshotListener(listener)
    }

    override fun onInactive() {
        documentReference.addSnapshotListener(listener)
    }

    private inner class MySnapshotListener : EventListener<DocumentSnapshot> {
        override fun onEvent(documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
            if (documentSnapshot != null)
                value = DtoConverter.documentSnapshotToUser(documentSnapshot)
        }

    }

}