package com.example.messenger.remote.repository

import com.example.messenger.ui.data.vo.UserListVO
import com.example.messenger.ui.data.vo.UserVO
import java.util.*

interface UserRepository {

    fun findById(id: Long): Observable<UserVO>
    fun all(): Observable<UserListVO>
    fun echoDetails(): Observable<UserVO>
}