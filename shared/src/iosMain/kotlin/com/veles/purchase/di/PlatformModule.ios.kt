package com.veles.purchase.di

import com.veles.purchase.platform.extractor.IosPriceEntityExtractor
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import com.veles.purchase.platform.scanner.IosTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    // Text Recognizer (OCR) — iOS: Apple Vision Framework
    single<TextRecognizer> { IosTextRecognizer() }

    // Price Entity Extractor — iOS: regex-based fallback
    single<PriceEntityExtractor> { IosPriceEntityExtractor() }
}
