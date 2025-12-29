package com.veles.purchase

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import com.veles.purchase.presentation.navigation.AppNavigation
import com.veles.purchase.presentation.navigation.Route
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

/**
 * Main App Composable for Kotlin Multiplatform
 *
 * This is the entry point for the shared UI.
 * Call this from:
 * - Android: MainActivity or your main Activity
 * - iOS: ContentView in SwiftUI
 *
 * Usage (Android):
 * ```kotlin
 * setContent {
 *     App(activity = this)
 * }
 * ```
 *
 * Usage (iOS - SwiftUI):
 * ```swift
 * var body: some View {
 *     ComposeView {
 *         AppKt.App(activity: nil)
 *     }
 * }
 * ```
 */
@Composable
fun App(activity: Any? = null) {
    // Ensure Koin context is available
    KoinContext {
        // Check if user is logged in
        val userRepository: FirebaseGetUserRepository = koinInject()
        val needsLogin = remember { userRepository.isNeedLogin() }

        MaterialTheme {
            Surface {
                AppNavigation(
                    startDestination = if (needsLogin) Route.Login else Route.Main,
                    activity = activity
                )
            }
        }
    }
}
