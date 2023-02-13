package com.veles.purchase.data.local.data

interface DataStore {
    fun setAccessToken(value: String)
    fun getAccessToken(): String
    fun setRefreshToken(value: String)
    fun setCityId(cityId: Int)
    fun getRefreshToken(): String
    fun getCityId(): Int
    fun setCityName(city: String)
    fun getCityName(): String
    fun setPinEncrypt(value: String)
    fun getPinEncrypt(): String
    fun setIV(encryptionIv: ByteArray)
    fun getIV(): ByteArray
    fun setFCMToken(value: String)
    fun getFCMToken(): String
    fun setIsUseFingerprint(value: Boolean)
    fun isUseFingerprint(): Boolean
    fun isInForeground(): Boolean
    fun setIsInForeground(value: Boolean)
    fun clear()
}
