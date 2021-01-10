package com.example.messenger.ui.login

import com.example.messenger.ui.base.BaseView
import com.example.messenger.ui.auth.AuthView

interface LoginView : BaseView, AuthView {
    fun showProgress()
    fun hideProgress()
    fun setUsernameError()
    fun setPasswordError()
    fun navigateToSingUp()
    fun navigatetoHome()
}