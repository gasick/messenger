package com.example.messenger.ui.signup

import com.example.messenger.ui.base.BaseView
import com.example.messenger.ui.auth.AuthView

interface SignUpView: BaseView, AuthView {

    fun showProgress()
    fun showSignUpError()
    fun hideProgress()
    fun setUsernameError()
    fun setPhoneNumberError()
    fun setPasswordError()
    fun navigateToHome()
}