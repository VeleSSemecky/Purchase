package com.veles.purchase.data.local.data

import android.content.SharedPreferences
import android.util.Base64
import com.veles.purchase.domain.utill.emptyString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DataStore {

    override fun setAccessToken(value: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, value)
            .apply()
    }

    override fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, emptyString()) ?: emptyString()
    }

    override fun setRefreshToken(value: String) {
        sharedPreferences.edit()
            .putString(REFRESH_TOKEN, value)
            .apply()
    }

    override fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, emptyString()) ?: emptyString()
    }

    override fun setCityId(cityId: Int) {
        sharedPreferences.edit()
            .putInt(CITY_ID, cityId)
            .apply()
    }

    override fun getCityId(): Int {
        return sharedPreferences.getInt(CITY_ID, -1)
    }

    override fun setCityName(city: String) {
        sharedPreferences.edit()
            .putString(CITY_NAME, city)
            .apply()
    }

    override fun getCityName(): String {
        return sharedPreferences.getString(CITY_NAME, emptyString()) ?: emptyString()
    }

    override fun setPinEncrypt(value: String) {
        sharedPreferences.edit()
            .putString(PIN_ENCRYPT, value)
            .apply()
    }

    override fun getPinEncrypt(): String {
        return sharedPreferences.getString(PIN_ENCRYPT, emptyString()) ?: emptyString()
    }

    override fun setIV(encryptionIv: ByteArray) {
        sharedPreferences.edit()
            .putString(PIN_IV, Base64.encodeToString(encryptionIv, Base64.DEFAULT))
            .apply()
    }

    override fun getIV(): ByteArray {
        return Base64.decode(sharedPreferences.getString(PIN_IV, emptyString()), Base64.DEFAULT)
    }

    override fun setFCMToken(value: String) {
        sharedPreferences.edit()
            .putString(FCM_TOKEN, value)
            .apply()
    }

    override fun getFCMToken(): String {
        return sharedPreferences.getString(FCM_TOKEN, emptyString()) ?: emptyString()
    }

    override fun setIsUseFingerprint(value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_USE_FINGERPRINT, value)
            .apply()
    }

    override fun isUseFingerprint(): Boolean {
        return sharedPreferences.getBoolean(IS_USE_FINGERPRINT, false)
    }

    override fun isInForeground(): Boolean {
        return sharedPreferences.getBoolean(IN_FOREGROUND, false)
    }

    override fun setIsInForeground(value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IN_FOREGROUND, value)
            .apply()
    }

    override fun clear() {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, "")
            .putString(REFRESH_TOKEN, "")
            .putString(PIN_ENCRYPT, "")
            .putString(PIN_IV, "")
            .putString(FCM_TOKEN, "")
            .putBoolean(IS_USE_FINGERPRINT, false)
            .apply()
    }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val FCM_TOKEN = "FCM_TOKEN"
        const val CITY_ID = "CITY_ID"
        const val CITY_NAME = "CITY_NAME"
        const val PIN_ENCRYPT = "PIN_ENCRYPT"
        const val PIN_IV = "PIN_IV"
        const val IS_USE_FINGERPRINT = "IS_USE_FINGERPRINT"
        const val IN_FOREGROUND = "IN_FOREGROUND"
    }
}
