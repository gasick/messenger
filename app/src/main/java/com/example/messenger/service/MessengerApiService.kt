package com.example.messenger.service

import com.example.messenger.remote.request.LoginRequestObject
import com.example.messenger.remote.request.MessageRequestObject
import com.example.messenger.remote.request.StatusUpdateRequestObject
import com.example.messenger.remote.request.UserRequestObject
import com.example.messenger.ui.data.vo.*
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import com.example.messenger.ui.data.vo.UserVO as UserVO1

interface MessengerApiService {
    @POST("login")
    @Headers("Content-Type: application/json")
    fun login(@Body user: LoginRequestObject): Observable<retrofit2.Response<ResponseBody>>

   @POST("users/registrations")
   fun createUser(@Body user: UserRequestObject): Observable<UserVO1>

   @GET("users")
   fun listUsers(@Headers("Authorization") authorization: String): Observable<UserListVO>

   @PUT("users")
   fun updateStatus(
       @Body request: StatusUpdateRequestObject,
       @Headers("Authorization") authorization: String
   ): Observable<UserVO1>

   @GET("users/{userId}")
   fun showUser(
       @Path("userId") userId: Long,
       @Headers("Authorization") authorization: String
   ): Observable<UserVO1>

   @GET("users/details")
   fun echoDetails(@Headers("Authorization") authorization: String): Observable<UserVO1>

   @POST("message")
   fun createMessage(
       @Body messageRequestObject: MessageRequestObject,
       @Headers ("Authorization") authorization: String
   ): Observable<MessageVO>

   @GET("conversation")
   fun listConversations(@Headers("Authorization") authorization: String): Observable<ConversationVO>

   @GET("conversations/{conversationId}")
   fun showConversation(
       @Path("conversationId") conversationId: Long,
       @Headers("Authorization") authorization: String
   ): Observable<ConversationVO>

   companion object Factory{
       private var service: MessengerApiService? = null

       fun getInstance(): MessengerApiService{
           if (service == null){
               val retrofit = Retrofit.Builder()
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                   .addConverterFactory(GsonConverterFactory.create())
                   .baseUrl("localhost:8080")
                   .build()

               service = retrofit.create(MessengerApiService::class.java)
           }
           return service as MessengerApiService
       }
   }
}
