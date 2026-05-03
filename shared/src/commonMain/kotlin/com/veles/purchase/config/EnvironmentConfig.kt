package com.veles.purchase.config

import kotlin.native.concurrent.ThreadLocal

/**
 * Environment configuration for KMP
 * Initialized with values from the platform (Android/iOS)
 */
@ThreadLocal
object EnvironmentConfig {
    // These will be initialized at app startup
    var DB_KEY: String = "default_db_key"
    var SERVER_CLIENT_ID: String = "76128650518-3vt5un7d8sv5jti8mfrb21v6ap4fpj6a.apps.googleusercontent.com"

    fun initialize(dbKey: String, serverClientId: String) {
        DB_KEY = dbKey
        SERVER_CLIENT_ID = serverClientId
    }

    // Firebase Collections
    const val COLLECTION_DATABASE = "FirebaseFirestore"
    const val COLLECTION_PURCHASE = "CollectionPurchase"
    const val USER_PURCHASE = "UserPurchase"
    const val PURCHASE = "purchase"
    const val LATER = "later"

    // User fields
    const val UID = "uid"
    const val FCM_TOKEN = "fcmToken"
    const val LIST_MEMBERS = "listMembers"
}
