package com.veles.purchase.config

import android.security.keystore.KeyProperties

object EnvironmentConfig {

    const val KEY_SIZE = 256
    const val ANDROID_KEYSTORE = "AndroidKeyStore"
    const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
    const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
    const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    const val SECRET_KEY_NAME = "SECRET_KEY_NAME"

    const val DB_KEY = BuildConfig.DB_KEY

    const val MESSAGE_AUTHENTICATOR_NAME = BuildConfig.MESSAGE_AUTHENTICATOR_NAME
    const val MESSAGE_AUTHENTICATOR_VALUE = BuildConfig.MESSAGE_AUTHENTICATOR_VALUE
    const val MESSAGE_API = BuildConfig.MESSAGE_API

    const val SERVER_CLIENT_ID = BuildConfig.SERVER_CLIENT_ID

    const val COLLECTION_DATABASE = "FirebaseFirestore"

    const val COLLECTION_PURCHASE = "CollectionPurchase"

    const val PURCHASE = "purchase"

    const val LATER = "later"

    const val USER_PURCHASE = "UserPurchase"

    const val LIST_MEMBERS = "listMembers"

    const val UID = "uid"

    const val FCM_TOKEN = "fcmToken"
}
