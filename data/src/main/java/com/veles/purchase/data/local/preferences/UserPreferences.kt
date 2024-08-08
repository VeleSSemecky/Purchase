package com.veles.purchase.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getUserUid(): String? = sharedPreferences.getString(USER_UID, null)

    fun saveUserUid(uid: String) = sharedPreferences.edit().putString(USER_UID, uid).apply()

    companion object {

        private const val USER_UID = "USER_UID"
    }
}
