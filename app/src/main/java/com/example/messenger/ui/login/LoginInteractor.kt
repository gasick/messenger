package com.example.messenger.ui.login

import com.example.messenger.ui.data.local.AppPreferences

interface LoginInteractor:AuthInteractor {
    interface OnDetailsRetrievalFinishedListener{
        fun onDetailsRetrievalSucces()
        fun onDetailsRetrivalError()
    }

    fun login(
        username: String,
        password: String,
        listener: AuthInteractor.onAuthFinishedListener
    )

    fun retrieveDetails(
        preferences: AppPreferences,
        listener: OnDetailsRetrievalFinishedListener
    )
}