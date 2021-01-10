package com.example.messenger.ui.auth

import com.example.messenger.ui.data.local.AppPreferences
import com.example.messenger.ui.data.vo.UserVO

interface AuthInteractor {

    var userDetails: UserVO
    var accessToken: String
    var submittedUsername: String
    var submittedPassword: String

    interface onAuthFihishedListener{
        fun onAuthSuccess()
        fun onAuthError()
        fun onUsernameError()
        fun onPasswordError()
    }

    fun persistAccessToken(preferences: AppPreferences)
    fun persistUserDetails(preferences: AppPreferences)

}