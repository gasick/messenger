package com.example.messenger.ui.chat

import android.content.Context
import com.example.messenger.remote.repository.ConversationRepositoryImpl
import com.example.messenger.remote.request.MessageRequestObject
import com.example.messenger.service.MessengerApiService
import com.example.messenger.ui.data.local.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatInteractorImpl(context: Context): ChatInteractor {
   private val preferences: AppPreferences = AppPreferences.create(context)
   private val service: MessengerApiService = MessengerApiService.getInstance()
   private val conversationRepository: ConversationRepositoryImpl = ConversationRepositoryImpl(context)


   override fun loadMessage(
      conversationId: Long,
      listener: ChatInteractor.onMessageLoadFinishedListener
   ) {
       conversationRepository.findConversationById(conversationId)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
             { res -> listener.onLoadSuccess(res)},
             { error ->
                listener.onLoadError()
                error.printStackTrace()
             }
          )
   }

   override fun sendMessage(
      recipientId: Long,
      message: String,
      listener: ChatInteractor.onMessageSendFinisedListener
   ) {
       service.createMessage(MessageRequestObject(
          recipientId,
          message
       ),
          preferences.accessToken as String
       )
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ _ ->
             listener.onSendSuccess()
          }, { error ->
             listener.onSendError()
             error.printStackTrace()
          })
   }
}
