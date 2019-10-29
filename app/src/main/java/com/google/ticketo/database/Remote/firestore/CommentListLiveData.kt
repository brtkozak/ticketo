package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.ticketo.model.Comment
import com.google.ticketo.model.DtoConverter

class CommentListLiveData(private val collectionReference: CollectionReference) : LiveData<List<Comment>>() {

    private val listener = MySnapshotListener()
    override fun onActive() {
        collectionReference.addSnapshotListener(listener)
    }

    override fun onInactive() {
        collectionReference.addSnapshotListener(listener)
    }

    private inner class MySnapshotListener : EventListener<QuerySnapshot> {
        override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
            if (querySnapshot != null)
                value = DtoConverter.querySnapshotToListOfComments(querySnapshot)
        }
    }
}