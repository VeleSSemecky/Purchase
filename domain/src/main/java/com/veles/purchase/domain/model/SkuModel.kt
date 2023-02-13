package com.veles.purchase.domain.model

import com.veles.purchase.domain.utill.emptyString
import java.time.LocalDateTime
import java.util.Currency
import java.util.Locale

data class SkuModel(
    val skuId: String,
    val skuLocalData: LocalDateTime = LocalDateTime.now(),
    val skuName: String = emptyString(),
    val skuComment: String = emptyString(),
    val skuPrice: String = emptyString(),
    val skuCurrencyCode: String = Currency.getInstance(Locale.getDefault()).currencyCode
)
