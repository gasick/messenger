package com.example.messenger.ui.main

import com.example.messenger.ui.base.BaseView

interface MainView: BaseView {
    fun showConversatinsLoadError()
    fun showContactsLoadError()
    fun showConversationsScreen()
    fun showContactsScreen()
    fun getContactsFragment(): MainActivity.ContactsFragment
    fun getconversationsFragment(): MainActivity.ConversationFragment
    fun showNoConversations()
    fun navigateToLogin()
    fun navigateToSettings()
}