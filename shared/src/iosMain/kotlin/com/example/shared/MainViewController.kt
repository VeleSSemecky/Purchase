package com.example.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.veles.purchase.App
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.di.initKoin
import org.koin.mp.KoinPlatform
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    // Initialize Koin if not already started
    try {
        KoinPlatform.getKoin()
    } catch (_: Exception) {
        EnvironmentConfig.initialize(
            dbKey = EnvironmentConfig.DB_KEY,
            serverClientId = EnvironmentConfig.SERVER_CLIENT_ID,
            cloudinaryCloudName = "dsvbwak3h",
            cloudinaryApiKey = "***REMOVED***",
            cloudinaryApiSecret = "***REMOVED***"
        )
        initKoin()
    }

    // Create the UIViewController
    val viewController = ComposeUIViewController {
        App(activity = null) // activity is not used directly for iOS sign-in
    }

    return viewController
}
