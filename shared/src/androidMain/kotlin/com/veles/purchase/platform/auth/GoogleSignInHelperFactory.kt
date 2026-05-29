package com.veles.purchase.platform.auth

import android.app.Activity

/**
 * Factory for creating GoogleSignInHelper instances on Android
 * This is needed because Activity is only available at runtime
 */
class GoogleSignInHelperFactory(private val serverClientId: String) {
    fun create(activity: Activity): GoogleSignInHelper = GoogleSignInHelper(activity, serverClientId)
}

/**
 * Actual implementation of factory function for Android
 */
actual fun createGoogleSignInHelper(activity: Any?, serverClientId: String): GoogleSignInHelper? = if (activity is Activity) {
    GoogleSignInHelper(activity, serverClientId)
} else {
    null
}
