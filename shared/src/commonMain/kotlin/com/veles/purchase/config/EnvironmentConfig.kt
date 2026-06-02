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

    // Cloudinary image storage
    var CLOUDINARY_CLOUD_NAME: String = ""
    var CLOUDINARY_API_KEY: String = ""
    var CLOUDINARY_API_SECRET: String = ""

    // Groq Cloud API Key
    var GROQ_API_KEY: String = ""

    // Kaggle credentials for downloading on-device models (e.g. Gemma 3n E4B)
    var KAGGLE_USERNAME: String = ""
    var KAGGLE_API_KEY: String = ""

    fun initialize(
        dbKey: String,
        serverClientId: String,
        cloudinaryCloudName: String = "",
        cloudinaryApiKey: String = "",
        cloudinaryApiSecret: String = "",
        groqApiKey: String = "",
        kaggleUsername: String = "",
        kaggleApiKey: String = ""
    ) {
        DB_KEY = dbKey
        SERVER_CLIENT_ID = serverClientId
        CLOUDINARY_CLOUD_NAME = cloudinaryCloudName
        CLOUDINARY_API_KEY = cloudinaryApiKey
        CLOUDINARY_API_SECRET = cloudinaryApiSecret
        GROQ_API_KEY = groqApiKey
        KAGGLE_USERNAME = kaggleUsername
        KAGGLE_API_KEY = kaggleApiKey
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
