package com.example.messenger.remote.repository

import com.example.messenger.ui.data.vo.ConversationVO
import java.util.*

interface ConverstationRepository {
    fun findConversationById(id: Long): Observable<ConversationVO>
    fun all(): Observable<ConversationVO>
}