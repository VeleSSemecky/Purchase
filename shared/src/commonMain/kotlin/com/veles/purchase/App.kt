package com.veles.purchase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.veles.purchase.domain.repository.user.FirebaseGetUserRepository
import com.veles.purchase.presentation.compose.MyTheme
import com.veles.purchase.presentation.navigation.AppNavigation
import com.veles.purchase.presentation.navigation.Route
import org.koin.compose.koinInject

/**
 * Main App Composable for Kotlin Multiplatform
 *
 * Entry point for shared UI — called from Android MainActivity and iOS ContentView.
 * MyTheme is applied here so both platforms get the same dark theme.
 */
@Composable
fun App(activity: Any? = null) {
    val userRepository: FirebaseGetUserRepository = koinInject()
    // Firebase Auth caches currentUser — this is safe to call synchronously
    val needsLogin = remember { userRepository.isNeedLogin() }

    MyTheme {
        AppNavigation(
            startDestination = if (needsLogin) Route.Login else Route.Main,
            activity = activity
        )
    }
}
