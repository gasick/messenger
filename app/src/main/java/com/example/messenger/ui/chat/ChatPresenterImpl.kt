package com.example.messenger.ui.chat

import android.widget.Toast
import com.example.messenger.ui.data.vo.ConversationVO
import com.example.messenger.utils.message.Message
import java.text.SimpleDateFormat

class ChatPresenterImpl(val view: ChatView):
        ChatPresenter,
        ChatInteractor.OnMessageLoadFinishedListener,
        ChatInteractor.OnMessageSendFinisedListener
{
   private val interactor: ChatInteractor = ChatInteractorImpl(view.getContext())

   override fun onLoadSuccess(conversationVO: ConversationVO) {
       val adapter = view.getMessageListAdapter()
       val dataFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
       conversationVO.messages.forEach{ message ->
           adapter.addToStart(
               Message(
                   message.senderId,
                   message.body,
                   dataFormatter.parse(message.createdAt.split(".")[0])
               ),
               true
               )
       }
   }

    override fun onLoadError() {
        view.showConversationLoadError()
    }

    override fun onLoadSuccess(){
        Toast.makeText(view.getContext(), "Message sent", Toast.LENGTH_SHORT).show()
    }

    override fun onSendError() {
        view.showMessageSendError()
    }

    override fun sendMessage(recipientId: Long, message: String) {
        interactor.sendMessage(recipientId, message, this)
    }

    override fun loadMessage(conversationId: Long) {
        interactor.loadMessage(conversationId, this)
    }
}