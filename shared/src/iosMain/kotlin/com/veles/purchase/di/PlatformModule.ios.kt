package com.veles.purchase.di

import com.veles.purchase.platform.extractor.IosPriceEntityExtractor
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import com.veles.purchase.platform.scanner.IosTextRecognizer
import com.veles.purchase.platform.scanner.TextRecognizer
import com.veles.purchase.platform.ai.GemmaDownloadState
import com.veles.purchase.platform.ai.LocalModelDownloader
import com.veles.purchase.platform.ai.ReceiptAiParser
import kotlinx.coroutines.flow.flowOf
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<TextRecognizer> { IosTextRecognizer() }

    single<PriceEntityExtractor> { IosPriceEntityExtractor() }

    single<LocalModelDownloader> {
        object : LocalModelDownloader {
            override fun downloadModel() = flowOf(GemmaDownloadState.Failed("Not supported on iOS"))
        }
    }

    single<ReceiptAiParser>(qualifier = named("nano")) {
        object : ReceiptAiParser {
            override val engineType = ReceiptAiParser.EngineType.GEMINI_NANO
            override suspend fun isAvailable() = false
            override suspend fun parse(imageBytes: ByteArray) = null
        }
    }

    single<ReceiptAiParser>(qualifier = named("local")) {
        object : ReceiptAiParser {
            override val engineType = ReceiptAiParser.EngineType.LOCAL_SLM
            override suspend fun isAvailable() = false
            override suspend fun parse(imageBytes: ByteArray) = null
        }
    }
}
