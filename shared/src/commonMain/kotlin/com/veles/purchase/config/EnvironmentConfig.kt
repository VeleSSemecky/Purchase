package com.veles.purchase.config

/**
 * Environment configuration for KMP
 * Firebase and app constants
 */
object EnvironmentConfig {

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

    // Google OAuth - Server Client ID for both Android and iOS
    const val SERVER_CLIENT_ID = "76128650518-3vt5un7d8sv5jti8mfrb21v6ap4fpj6a.apps.googleusercontent.com"

    // These will be provided via BuildConfig or environment
    // For now, using placeholder values
    const val DB_KEY = "default_db_key"  // TODO: Replace with actual BuildConfig value
}

