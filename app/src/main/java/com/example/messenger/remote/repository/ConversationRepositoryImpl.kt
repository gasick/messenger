package com.example.messenger.remote.repository

import android.content.Context
import com.example.messenger.service.MessengerApiService
import com.example.messenger.ui.data.local.AppPreferences
import com.example.messenger.ui.data.vo.ConversationVO
import java.util.*

class ConversationRepositoryImpl(ctx: Context): ConverstationRepository {
    private val preferences: AppPreferences = AppPreferences.create(ctx)
    private val service: MessengerApiService = MessengerApiService.getInstance()

    override fun findConversationById(id: Long): Observable<ConversationVO> {
        return service.showConversation(id, preferences.accessToken as String)
    }

    override fun all(): Observable<ConversationVO>{
        return service.listConversations(preferences.accessToken as String)
    }


}