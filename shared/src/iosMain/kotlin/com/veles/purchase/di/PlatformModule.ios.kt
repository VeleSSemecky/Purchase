package com.veles.purchase.di

import com.veles.purchase.platform.extractor.IosPriceEntityExtractor
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import com.veles.purchase.platform.scanner.IosTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import com.veles.purchase.platform.ai.IosReceiptAiParser
import com.veles.purchase.platform.ai.IosGemmaModelRepository
import com.veles.purchase.platform.ai.GemmaModelRepository
import com.veles.purchase.platform.ai.ReceiptAiParser
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    // Text Recognizer (OCR) — iOS: Apple Vision Framework
    single<TextRecognizer> { IosTextRecognizer() }

    // Price Entity Extractor — iOS: regex-based fallback
    single<PriceEntityExtractor> { IosPriceEntityExtractor() }

    // On-device AI receipt parser — iOS: Foundation Models planned (iOS 26+)
    single<ReceiptAiParser> { IosReceiptAiParser(fallback = get()) }

    // Gemma model repository — iOS: stub (not downloaded)
    single<GemmaModelRepository> { IosGemmaModelRepository() }
}
