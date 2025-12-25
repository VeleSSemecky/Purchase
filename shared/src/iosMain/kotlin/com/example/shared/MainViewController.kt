package com.example.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.veles.purchase.App
import com.veles.purchase.di.initKoinMockData
import org.koin.mp.KoinPlatform
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    // Initialize Koin if not already started
    try {
        KoinPlatform.getKoin()
    } catch (_: Exception) {
        initKoinMockData()
    }

    return ComposeUIViewController {
        App(activity = null)
    }
}
