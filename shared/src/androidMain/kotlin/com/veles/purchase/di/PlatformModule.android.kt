package com.veles.purchase.di

import com.veles.purchase.platform.auth.GoogleSignInHelper
import com.veles.purchase.platform.extractor.AndroidPriceEntityExtractor
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import com.veles.purchase.platform.ai.*
import com.veles.purchase.platform.scanner.AndroidTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import com.veles.purchase.platform.scanner.YCoordinateTextRecognizer
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {

    factory { (activity: android.app.Activity, serverClientId: String) ->
        GoogleSignInHelper(activity, serverClientId)
    }

    single<TextRecognizer> { AndroidTextRecognizer() }
    
    // Y-Coordinate Sorting OCR
    single<TextRecognizer>(qualifier = named("y_sorted")) { YCoordinateTextRecognizer() }

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

    // Engine 4: OCR + Text SLM (Fallback)
    single<ReceiptAiParser>(qualifier = named("ocr_text")) {
        AndroidOcrTextParser(
            context = androidContext(),
            textRecognizer = get(named("y_sorted")),
            modelFile = get<LocalModelManager>().modelFile
        )
    }
}
