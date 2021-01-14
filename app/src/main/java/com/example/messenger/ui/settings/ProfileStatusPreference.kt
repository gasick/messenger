package com.example.messenger.ui.settings

import android.content.Context
import android.preference.EditTextPreference
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.Toast
import com.example.messenger.remote.request.StatusUpdateRequestObject
import com.example.messenger.service.MessengerApiService
import com.example.messenger.ui.data.local.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.jar.Attributes

class ProfileStatusPreference(
        context: Context,
        attributeSet: AttributeSet
): EditTextPreference(context, attributeSet) {
    private val service: MessengerApiService = MessengerApiService.getInstance()
    private val preferences: AppPreferences = AppPreferences.create(context)

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            val etStatus = editText
            if (TextUtils.isEmpty(etStatus.text)) {
                Toast.makeText(context, "Status cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                val requestObject = StatusUpdateRequestObject(etStatus.text.toString())

                service.updateUserStatus(
                        requestObject,
                        preferences.accessToken as String
                )
                        .subscribeOn(Schedulers.io())
                        .observerOn(AndroidSchedulers.mainThread())
                        .subscribe({ res ->
                            preferences.storeUserDetails(res)
                        }, { error ->
                            Toast.makeText(context, "Unable to update status at the moment. Try again later", Toast.LENGTH_LONG).show()
                            error.printStackTrace()
                        })
            }
        }
    }
}