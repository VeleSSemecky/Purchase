package com.veles.purchase.platform.auth

import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSLog
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * iOS implementation of GoogleSignInHelper
 *
 * This implementation uses NotificationCenter to communicate with Swift.
 * Swift side handles the actual Google Sign-In via GoogleSignInBridge.
 */
actual class GoogleSignInHelper(
    private val viewController: Any?,
    private val serverClientId: String
) {
    init {
        NSLog("GoogleSignInHelper: init() called")
    }

    actual suspend fun signIn(): Pair<String, String?> = suspendCoroutine { continuation ->
        NSLog("GoogleSignInHelper: signIn() called")

        var observer: NSObject? = null

        // Create observer for the result
        observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = "GoogleSignInResult",
            `object` = null,
            queue = null
        ) { notification ->
            NSLog("GoogleSignInHelper: Received GoogleSignInResult notification")

            // Remove observer after receiving result
            observer?.let { NSNotificationCenter.defaultCenter.removeObserver(it) }

            val userInfo = notification?.userInfo
            val idToken = userInfo?.get("idToken") as? String
            val accessToken = userInfo?.get("accessToken") as? String
            val error = userInfo?.get("error") as? String

            NSLog("GoogleSignInHelper: idToken=$idToken, accessToken=$accessToken, error=$error")

            when {
                error != null && error != "null" -> {
                    continuation.resumeWithException(Exception(error))
                }
                idToken != null && idToken != "null" && accessToken != null && accessToken != "null" -> {
                    continuation.resume(idToken to accessToken)
                }
                else -> {
                    continuation.resumeWithException(Exception("Unknown error during Google Sign-In"))
                }
            }
        } as NSObject

        NSLog("GoogleSignInHelper: Posting GoogleSignInRequest notification")

        // Post notification to trigger sign-in on Swift side
        NSNotificationCenter.defaultCenter.postNotificationName(
            aName = "GoogleSignInRequest",
            `object` = null
        )

        NSLog("GoogleSignInHelper: GoogleSignInRequest notification posted")
    }
}

/**
 * Factory function to create GoogleSignInHelper on iOS
 */
actual fun createGoogleSignInHelper(activity: Any?, serverClientId: String): GoogleSignInHelper? {
    return GoogleSignInHelper(activity, serverClientId)
}


