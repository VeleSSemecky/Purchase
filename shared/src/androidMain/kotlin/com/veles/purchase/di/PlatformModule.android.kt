package com.veles.purchase.di

import com.veles.purchase.platform.auth.GoogleSignInHelper
import com.veles.purchase.platform.extractor.AndroidPriceEntityExtractor
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import com.veles.purchase.platform.scanner.AndroidTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import com.veles.purchase.platform.ai.AndroidGemmaModelRepository
import com.veles.purchase.platform.ai.AndroidReceiptAiParser
import com.veles.purchase.platform.ai.GemmaModelRepository
import com.veles.purchase.platform.ai.ReceiptAiParser
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    factory { (activity: android.app.Activity, serverClientId: String) ->
        GoogleSignInHelper(activity, serverClientId)
    }

    // Text Recognizer (OCR) — Android: ML Kit
    single<TextRecognizer> { AndroidTextRecognizer() }

    // Price Entity Extractor — Android: ML Kit Entity Extraction (on-device Polish model)
    single<PriceEntityExtractor> { AndroidPriceEntityExtractor() }

    // Gemma 3 model repository — handles download + status
    single<GemmaModelRepository> { AndroidGemmaModelRepository(androidContext()) }

    // On-device AI receipt parser — Android: Gemma 3 via MediaPipe (if downloaded)
    single<ReceiptAiParser> {
        AndroidReceiptAiParser(
            context = androidContext(),
            modelRepo = get<GemmaModelRepository>() as AndroidGemmaModelRepository,
            fallback = get()
        )
    }
}
