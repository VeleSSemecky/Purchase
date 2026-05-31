package com.veles.purchase.platform.ai

import com.veles.purchase.domain.model.scanner.ReceiptData
import com.veles.purchase.domain.usecase.scanner.ParseReceiptUseCase

/**
 * iOS implementation of [ReceiptAiParser].
 *
 * On-device AI via Foundation Models (iOS 26+) is planned.
 * Currently returns null to let [ParseReceiptUseCase] handle parsing.
 */
class IosReceiptAiParser(
    @Suppress("UNUSED_PARAMETER") fallback: ParseReceiptUseCase
) : ReceiptAiParser {

    override suspend fun isAvailable(): Boolean = false

    override suspend fun parse(lines: List<String>): ReceiptData? = null
}
