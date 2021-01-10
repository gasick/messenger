package com.example.messenger.ui.main

import android.content.Context
import com.example.messenger.remote.repository.ConversationRepositoryImpl
import com.example.messenger.remote.repository.ConverstationRepository
import com.example.messenger.remote.repository.UserRepository
import com.example.messenger.remote.repository.UserRepositoryImpl
import com.example.messenger.ui.data.local.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainInteractorImpl(val context: Context): MainInteractor {
    private val userRepository: UserRepository = UserRepositoryImpl(context)
    private val converstationRepository: ConverstationRepository = ConversationRepositoryImpl(context)
    override fun loadContacts(listener: MainInteractor.OnContactsLoadFinishedListener) {
        userRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    listener.onContactsLoadSuccess(res)
                }, { error ->
                    listener.onContactsLoadError()
                    error.printStackTrace()
                }
            )
    }

    override fun loadConverstaions(listener: MainInteractor.OnConversationsLoadFinishedListener){
        converstationRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    listener.onConversationsLoadSuccess(res)
                }, { error ->
                    listener.onConversationsLoadError()
                    error.printStackTrace()
                }
            )
    }

    override fun logout(listener: MainInteractor.OnLogoutFinishedListener){
        val preferences: AppPreferences = AppPreferences.create(context)
        preferences.clear()
        listener.onLogoutSuccess()
    }
}