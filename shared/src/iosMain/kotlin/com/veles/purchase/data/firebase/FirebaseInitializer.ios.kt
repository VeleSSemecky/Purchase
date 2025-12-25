package com.veles.purchase.data.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.app
import dev.gitlive.firebase.initialize

/**
 * iOS implementation of FirebaseInitializer
 * Initializes Firebase using GoogleService-Info.plist
 */
actual class FirebaseInitializer {
    actual fun initialize() {
        // Initialize Firebase with default configuration
        // Firebase will read from GoogleService-Info.plist
        try {
            Firebase.initialize()
        } catch (e: Exception) {
            // Firebase might already be initialized
            println("Firebase initialization: ${e.message}")
        }
    }

    actual fun getApp(): FirebaseApp {
        return Firebase.app
    }
}

