package com.google.ticketo.ui.login

import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken

class LoginViewModel {
    private val userRepository = UserStorer()

    fun isLoading(): MutableLiveData<LoginStatus> = userRepository.loginStatus

    fun login(token: AccessToken) = userRepository.loginUser(token)

}