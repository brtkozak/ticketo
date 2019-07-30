package com.google.ticketo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.Database.Remote.FacebookApi
import com.google.ticketo.Model.Responses.UserResponse
import com.google.ticketo.Model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var callbackManager: CallbackManager
    private lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        callbackManager = CallbackManager.Factory.create()

        val permissions = mutableListOf("public_profile", "user_friends", "user_link")
        login_button.setPermissions(permissions)

        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                // ...
            }

            override fun onError(error: FacebookException) {
                // ...
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        fireAuth = FirebaseAuth.getInstance()
        fireAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    getUserData(token)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
//                    updateUI(null)
                }
            }
    }

    private fun getUserData(token: AccessToken) {
        val facebookApi = FacebookApi.create()

        facebookApi.getUser(token.userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> storeUser(result!!) }, { error ->
                Log.d("tag", error.toString())
            })
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
            .addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
                override fun onComplete(task: Task<DocumentSnapshot>) {
                    if (task.result!!.exists()) {
                        Log.d("tag", "exist")
                    } else {
                        usersCollection.document(fireAuth.uid!!).set(user)
                        Log.d("tag", "not")
                    }
                }
            })

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
