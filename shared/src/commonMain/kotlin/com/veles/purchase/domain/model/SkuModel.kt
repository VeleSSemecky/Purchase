@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.domain.model

import com.veles.purchase.domain.core.platform.getDefaultCurrencyCode
import com.veles.purchase.domain.utill.emptyString
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

data class SkuModel(
    val skuId: String,
    val skuLocalData: LocalDateTime = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val skuName: String = emptyString(),
    val skuComment: String = emptyString(),
    val skuPrice: String = emptyString(),
    val skuCurrencyCode: String = getDefaultCurrencyCode() // Platform-specific currency detection
)
