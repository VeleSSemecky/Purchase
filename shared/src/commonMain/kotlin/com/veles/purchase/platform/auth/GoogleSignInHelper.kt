package com.veles.purchase.platform.auth

/**
 * Platform-specific Google Sign-In helper
 * Provides ID token for Firebase authentication
 */
expect class GoogleSignInHelper {
    /**
     * Initiate Google Sign-In flow
     * @return ID token for Firebase authentication
     * @throws Exception if sign-in fails or is cancelled
     */
    suspend fun signIn(): Pair<String, String?>
}

/**
 * Factory function to create GoogleSignInHelper from common code
 * Must be implemented in platform-specific code
 */
expect fun createGoogleSignInHelper(activity: Any?, serverClientId: String): GoogleSignInHelper?
