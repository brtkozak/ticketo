package com.google.ticketo.ui.login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.ticketo.MainActivity
import com.google.ticketo.R
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    val viewModel: LoginViewModel = LoginViewModel()
    private var callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        setupFacebookButton()
        observe()

    }

    private fun observe() {
        viewModel.isLoading().observe(this, Observer {
            if (it == LoginStatus.InProgress) {
                login_activity_progress_bar.visibility = View.VISIBLE
                login_activity_login_button.visibility = View.GONE
            } else if (it == LoginStatus.Login) {
                openMainAcitivity()
            }
        })
    }

    private fun setupFacebookButton() {
        val permissions = mutableListOf("public_profile", "user_friends", "user_link")
        login_activity_login_button.setPermissions(permissions)

        login_activity_login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("booltag", "button")

                viewModel.login(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("booltag", "cancel")

                // ...
            }

            override fun onError(error: FacebookException) {
                // ...
                Log.d("booltag", error.toString())
            }
        })
    }

    private fun openMainAcitivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        Log.d("booltag", "callback")

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}

enum class LoginStatus {
    Logout, Login, InProgress
}


