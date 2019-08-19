package com.google.ticketo.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.database.Remote.facebook.FacebookRepository
import com.google.ticketo.model.Responses.userResponse.UserResponse
import com.google.ticketo.model.User

class UserStorer {

    private val callbackManager = CallbackManager.Factory.create()
    private val fireAuth = FirebaseAuth.getInstance()
    val loginStatus: MutableLiveData<LoginStatus> = MutableLiveData(LoginStatus.Logout)


    fun loginUser(token: AccessToken) {
        loginStatus.value = LoginStatus.InProgress
        val credential = FacebookAuthProvider.getCredential(token.token)
        fireAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    FacebookRepository.getInstance().getCurrentUserData(token.userId).subscribe { response -> storeUser(response) }
                    //                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //                    updateUI(null)
                }
            }
    }

    private fun storeUser(userResponse: UserResponse) {
        val fireDb = FirebaseFirestore.getInstance()
        val usersCollection = fireDb.collection("users")

        val user = User(
            userResponse.id,
            fireAuth.uid,
            userResponse.name,
            userResponse.picture.data.url,
            userResponse.friends.summary.totalCount,
            userResponse.link
        )

        Log.d("tag", user.toString())

        usersCollection.document(fireAuth.uid!!).get()
            .addOnCompleteListener { task ->
                if (!task.result!!.exists()) {
                    usersCollection.document(fireAuth.uid!!).set(user)
                }
                loginStatus.value = LoginStatus.Login
            }
    }
}