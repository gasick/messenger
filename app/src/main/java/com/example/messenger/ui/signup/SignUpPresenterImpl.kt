package com.example.messenger.ui.signup

import com.example.messenger.ui.auth.AuthInteractor
import com.example.messenger.ui.data.local.AppPreferences

class SignUpPresenterImpl(
    private val view: SignUpView
    ):
        SignUpPreseneter,
        SignUpInteractor.OnSignUpFinishedListener,
        AuthInteractor.onAuthFinishedListener
{

    private val interactor: SignUpInteractor = SignUpInteractorImpl()
    override var preferences: AppPreferences = AppPreferences.create(view.getContext)

    override fun executeSignUp(
        username: String,
        phoneNumber: String,
        password: String
    ) {
        view.showProgress()
        interactor.signUp(
            username,
            phoneNumber,
            password,
            this
        )
    }

    override fun onSuccess() {
        interactor.getAuthorization(this)
    }

    override fun onAuthSuccess() {
        interactor.persistAccessToken(preferences)
        interactor.persistUserDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onAuthError() {
        view.hideProgress()
        view.showAuthError()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }


    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }


    override fun onPhoneNumberError() {
        view.hideProgress()
        view.setPhoneNumberError()
    }

    override fun onError() {
        view.hideProgress()
        view.showSignUpError()
    }
}