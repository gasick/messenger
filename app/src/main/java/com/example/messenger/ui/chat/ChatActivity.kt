package com.example.messenger.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messenger.R
import com.example.messenger.ui.data.local.AppPreferences
import com.example.messenger.utils.message.Message
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter

class ChatActivity :
        AppCompatActivity(),
        ChatView,
        MessageInput.InputListener
{

    private var recipientId: Long = -1
    private lateinit var messageList: MessagesList
    private lateinit var messageInput: MessageInput
    private lateinit var preferences: AppPreferences
    private lateinit var presenter: ChatPresenter
    private lateinit var messageListAdapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("RECIPIENT_NAME")

        preferences = AppPreferences.create(this)
        messageListAdapter = MessagesListAdapter(
            preferences.userDetails.id.toString(),
            null
        )
        presenter = ChatPresenterImpl(this)
        bindViews()

        val conversationId = intent.getLongExtra("CONVERSATION_ID", -1)
        recipientId = intent.getLongExtra("RECIPIENT_ID", -1)

        if(conversationId != -1L){
            presenter.loadMessage(conversationId)
        }
    }
}