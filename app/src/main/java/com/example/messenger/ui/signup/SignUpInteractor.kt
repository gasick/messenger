package com.example.messenger.ui.signup

import com.example.messenger.ui.auth.AuthInteractor

interface SignUpInteractor: AuthInteractor {

    interface OnSignUpFinishedListener {
        fun onSuccess()
        fun onUsernameError()
        fun onPasswordError()
        fun onPhoneNumberError()
        fun onError()
    }

    fun singUp(
        username: String,
        phoneNumber: String,
        password: String,
        listener: OnSignUpFinishedListener
    )

    fun getAuthorization(listener: AuthInteractor.onAuthFihishedListener)
}