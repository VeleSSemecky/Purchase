package com.veles.purchase.di

import com.veles.purchase.platform.auth.GoogleSignInHelper
import com.veles.purchase.platform.extractor.AndroidPriceEntityExtractor
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import com.veles.purchase.platform.scanner.AndroidTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import com.veles.purchase.platform.ai.AndroidGeminiNanoParser
import com.veles.purchase.platform.ai.LocalMediaPipeVisionParser
import com.veles.purchase.platform.ai.LocalModelDownloader
import com.veles.purchase.platform.ai.LocalModelManager
import com.veles.purchase.platform.ai.ReceiptAiParser
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {

    factory { (activity: android.app.Activity, serverClientId: String) ->
        GoogleSignInHelper(activity, serverClientId)
    }

    single<TextRecognizer> { AndroidTextRecognizer() }

    single<PriceEntityExtractor> { AndroidPriceEntityExtractor() }

    single<LocalModelManager> {
        LocalModelManager(
            context = androidContext(),
            kaggleApiKey = com.veles.purchase.config.EnvironmentConfig.KAGGLE_API_KEY
        )
    }
    single<LocalModelDownloader> { get<LocalModelManager>() }

    single<ReceiptAiParser>(qualifier = named("nano")) {
        AndroidGeminiNanoParser(androidContext())
    }

    single<ReceiptAiParser>(qualifier = named("local")) {
        LocalMediaPipeVisionParser(
            context = androidContext(),
            modelFile = get<LocalModelManager>().modelFile
        )
    }
}
