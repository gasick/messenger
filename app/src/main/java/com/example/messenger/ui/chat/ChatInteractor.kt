package com.example.messenger.ui.chat

import com.example.messenger.ui.data.vo.ConversationVO

interface ChatInteractor {
    interface OnMessageSendFinisedListener{
        fun onSendSuccess()
        fun onSendError()
    }

    interface OnMessageLoadFinishedListener{
        fun onLoadSuccess(conversationVO: ConversationVO)
        fun onLoadError()
    }

    fun sendMessage(
        recipientId: Long,
        message: String,
        listener: OnMessageSendFinisedListener
    )

    fun loadMessage(
        conversationId: Long,
        listener: OnMessageLoadFinishedListener
    )
}