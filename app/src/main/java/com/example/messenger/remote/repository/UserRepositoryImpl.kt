package com.example.messenger.remote.repository

import android.content.Context
import com.example.messenger.service.MessengerApiService
import com.example.messenger.ui.data.local.AppPreferences
import com.example.messenger.ui.data.vo.UserListVO
import com.example.messenger.ui.data.vo.UserVO
import java.util.*

class UserRepositoryImpl(ctx: Context): UserRepository {
    private val preferences: AppPreferences = AppPreferences.create(ctx)
    private val service: MessengerApiService = MessengerApiService.getInstance()

    override fun findById(id: Long): Observable<UserVO>{
        return service.showUser(id, preferences.accessToken as String)
    }

    override fun all(): Observable<UserListVO> {
        return service.listUsers(preferences.accessToken as String)
    }

    override fun echoDetails(): Observable<UserVO>{
        return service.echoDetails(preferences.accessToken as String)
    }
}