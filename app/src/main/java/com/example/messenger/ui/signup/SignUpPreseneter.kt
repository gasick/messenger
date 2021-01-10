package com.example.messenger.ui.signup

import com.example.messenger.ui.data.local.AppPreferences

interface SignUpPreseneter {
    var preferences: AppPreferences

    fun executeSignUp(
        username: String,
        phoneNumber: String,
        password: String
    )
}