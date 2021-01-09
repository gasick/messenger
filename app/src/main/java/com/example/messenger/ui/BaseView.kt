package com.example.messenger.ui

import android.content.Context

interface BaseView {
    fun bindViews()
    fun getContext(): Context
}