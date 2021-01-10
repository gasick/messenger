package com.example.messenger.ui.login

import com.example.messenger.ui.auth.AuthInteractor
import com.example.messenger.ui.data.local.AppPreferences

class LoginPresenterImpl(private val view: LoginView):
        LoginPresenter,
        AuthInteractor.onAuthFihishedListener,
        LoginInteractor.OnDetailsRetrievalFinishedListener {
    private val interactor: LoginInteractor = LoginInteractorImpl()
    private val preferences: AppPreferences = AppPreferences.create(view.getContext())

    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }

    override fun onAuthSuccess() {
        interactor.persistAccessToken(preferences)
        interactor.retrieveDetails(preferences, this)
    }

    override fun onAuthError() {
        view.showAuthError()
        view.hideProgress()
    }

    override fun onDetailsRetrivalError() {
       interactor.persistUserDetails(preferences)
        view.hideProgress()
        view.navigatetoHome()
    }

    override fun onDetailsRetrievalSucces() {
       interactor.retrieveDetails(preferences, this)
    }

    override fun executeLogin(
        username: String,
        password: String) {
        view.showProgress()
        interactor.login(username, password, this)

    }

}
