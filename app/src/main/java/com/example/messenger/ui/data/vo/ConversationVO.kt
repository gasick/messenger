package com.example.messenger.ui.data.vo

data class ConversationVO(
    val conversationId: Long,
    val secondPartyUsername: String,
    val messages: ArrayList<MessageVO>
)
