package com.veles.purchase

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.veles.purchase.presentation.navigation.AppNavigation
import com.veles.purchase.presentation.navigation.Route
import org.koin.compose.KoinContext

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
 *     App()
 * }
 * ```
 *
 * Usage (iOS - SwiftUI):
 * ```swift
 * var body: some View {
 *     ComposeView {
 *         AppKt.App()
 *     }
 * }
 * ```
 */
@Composable
fun App() {
    // Ensure Koin context is available
    KoinContext {
        MaterialTheme {
            Surface {
                AppNavigation(
                    startDestination = Route.Main
                )
            }
        }
    }
}

