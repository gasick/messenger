package com.example.messenger.remote.repository

import com.example.messenger.ui.data.vo.ConversationVO
import io.reactivex.Observable

interface ConverstationRepository {
    fun findConversationById(id: Long): Observable<ConversationVO>
    fun all(): Observable<ConversationVO>
}