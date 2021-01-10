package com.example.messenger.ui.signup

import android.text.TextUtils
import com.example.messenger.remote.request.LoginRequestObject
import com.example.messenger.remote.request.UserRequestObject
import com.example.messenger.service.MessengerApiService
import com.example.messenger.ui.auth.AuthInteractor
import com.example.messenger.ui.data.local.AppPreferences
import com.example.messenger.ui.data.vo.UserVO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpInteractorImpl() : SignUpInteractor {
    override lateinit var userDetails: UserVO
    override lateinit var accessToken: String
    override lateinit var submittedUsername: String
    override lateinit var submittedPassword: String

    private val service: MessengerApiService = MessengerApiService.getInstance()

    override fun singUp(
        username: String,
        phoneNumber: String,
        password: String,
        listener: SignUpInteractor.OnSignUpFinishedListener
    ) {
        submittedUsername = username
        submittedPassword = password
        val userRequestObject = UserRequestObject(username, password, phoneNumber)

        when {
            TextUtils.isEmpty(username) -> listener.onUsernameError()
            TextUtils.isEmpty(phoneNumber) -> listener.onPhoneNumberError()
            TextUtils.isEmpty(password) -> listener.onPasswordError()
            else -> {
                service.createUser(userRequestObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { res ->
                            userDetails = res
                            listener.onSuccess()
                        }, { error ->
                            listener.onError()
                            error.printStackTrace()
                        }
                    )
            }
        }
    }

    override fun getAuthorization(listener: AuthInteractor.onAuthFinishedListener) {
        val userRequestObject = LoginRequestObject(
            submittedUsername,
            submittedPassword
        )
        service.login(userRequestObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    accessToken = res.headers()["Authorization"] as String
                    listener.onAuthSuccess()
                }, { error ->
                    listener.onAuthError()
                    error.printStackTrace()
                }
            )
    }

    override fun persistAccessToken(preferences: AppPreferences) {
        preferences.storeAccessToken(accessToken)
    }

    override fun persistUserDetails(preferences: AppPreferences) {
        preferences.storeUserDetails(userDetails)
    }
}