package com.veles.purchase.data.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.app
import dev.gitlive.firebase.initialize

/**
 * Android implementation of FirebaseInitializer
 * Firebase is already initialized in the Application class,
 * so we just need to get the instance
 */
actual class FirebaseInitializer {
    actual fun initialize() {
        // Firebase is already initialized in Android Application class
        // via google-services.json
        // No additional initialization needed
    }

    actual fun getApp(): FirebaseApp {
        return Firebase.app
    }
}

