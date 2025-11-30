package com.veles.purchase.domain.model

import com.veles.purchase.domain.utill.currentLocalDateTime
import com.veles.purchase.domain.utill.emptyString

data class SkuModel(
    val skuId: String,
    val skuLocalData: Long = currentLocalDateTime(),
    val skuName: String = emptyString(),
    val skuComment: String = emptyString(),
    val skuPrice: String = emptyString(),
    val skuCurrencyCode: String = "USD" // Default currency for KMP
)


