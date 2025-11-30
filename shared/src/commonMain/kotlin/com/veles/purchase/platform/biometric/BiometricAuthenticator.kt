package com.veles.purchase.platform.biometric

/**
 * Sealed class representing biometric authentication results
 */
sealed class BiometricResult {
    /**
     * Authentication was successful
     */
    data object Success : BiometricResult()

    /**
     * Authentication failed with an error
     */
    data class Error(val message: String, val errorCode: Int = 0) : BiometricResult()

    /**
     * User cancelled the authentication
     */
    data object Cancelled : BiometricResult()

    /**
     * Biometric not available on device
     */
    data object NotAvailable : BiometricResult()

    /**
     * No biometric enrolled
     */
    data object NotEnrolled : BiometricResult()
}

/**
 * Platform-independent biometric authentication interface
 *
 * Usage:
 * ```kotlin
 * val authenticator = BiometricAuthenticator()
 * val result = authenticator.authenticate(
 *     title = "Authenticate",
 *     subtitle = "Use biometric to login"
 * )
 * when (result) {
 *     is BiometricResult.Success -> // Handle success
 *     is BiometricResult.Error -> // Handle error
 *     is BiometricResult.Cancelled -> // Handle cancellation
 *     else -> // Handle other cases
 * }
 * ```
 */
expect class BiometricAuthenticator {
    /**
     * Authenticate user with biometric
     *
     * @param title Title shown in biometric prompt
     * @param subtitle Subtitle/description shown in prompt
     * @param negativeButtonText Text for negative/cancel button
     * @return BiometricResult indicating success or failure
     */
    suspend fun authenticate(
        title: String,
        subtitle: String,
        negativeButtonText: String = "Cancel"
    ): BiometricResult

    /**
     * Check if biometric authentication is available on device
     */
    fun isBiometricAvailable(): Boolean

    /**
     * Check if biometric is enrolled (user has registered fingerprint/face)
     */
    fun isBiometricEnrolled(): Boolean
}


