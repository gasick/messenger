package com.example.messenger.remote.request

data class MessageRequestObject(
        val recipientId: Long,
        val message: String
)
