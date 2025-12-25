package com.veles.purchase.data.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp

/**
 * Firebase configuration and initialization
 * Platform-specific implementation via expect/actual
 */
expect class FirebaseInitializer {
    /**
     * Initialize Firebase for the platform
     * Android: Already initialized in Application class
     * iOS: Needs to call Firebase.initialize()
     */
    fun initialize()

    /**
     * Get Firebase app instance
     */
    fun getApp(): FirebaseApp
}

