package com.example.androidapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.veles.purchase.App

/**
 * Main Activity для Purchase KMP app
 *
 * Phase 2.4 - Early Test Integration
 * Phase 2.11 - Changed to FragmentActivity for biometric support
 *
 * Використовує App() composable з shared модуля
 */
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // App з shared модуля!
                    App(activity = this)
                }
            }
        }
    }
}

