package com.veles.purchase.di

import com.veles.purchase.platform.auth.GoogleSignInHelper
import com.veles.purchase.platform.scanner.AndroidTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    // Firebase Initializer
//    single {
//        FirebaseInitializer().apply {
//            initialize(null)
//        }
//    }

    // Google Sign-In Helper
    factory { (activity: android.app.Activity, serverClientId: String) ->
        GoogleSignInHelper(activity, serverClientId)
    }

    // Text Recognizer (OCR) — Android: ML Kit
    single<TextRecognizer> { AndroidTextRecognizer() }
}
