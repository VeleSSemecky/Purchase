package com.veles.purchase.platform.biometric

import kotlinx.coroutines.delay

/**
 * iOS implementation of BiometricAuthenticator
 *
 * TODO Phase 5: Implement using LocalAuthentication framework
 * - LAContext for biometric authentication
 * - Touch ID / Face ID support
 *
 * For now, returns stub implementation for Phase 2-3
 */
actual class BiometricAuthenticator {

    actual suspend fun authenticate(
        title: String,
        subtitle: String,
        negativeButtonText: String
    ): BiometricResult {
        // TODO: Implement iOS biometric authentication
        // Using LAContext.evaluatePolicy()
        delay(500) // Simulate delay
        return BiometricResult.NotAvailable
    }

    actual fun isBiometricAvailable(): Boolean {
        // TODO: Check if biometric is available on iOS device
        // LAContext.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics)
        return false
    }

    actual fun isBiometricEnrolled(): Boolean {
        // TODO: Check if biometric is enrolled
        return false
    }
}

/**
 * iOS Biometric implementation notes for Phase 5:
 *
 * import platform.LocalAuthentication.*
 *
 * val context = LAContext()
 * val policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics
 *
 * if (context.canEvaluatePolicy(policy, null)) {
 *     context.evaluatePolicy(
 *         policy,
 *         localizedReason = subtitle,
 *         reply = { success, error ->
 *             if (success) {
 *                 // BiometricResult.Success
 *             } else {
 *                 // Handle error
 *             }
 *         }
 *     )
 * }
 */

