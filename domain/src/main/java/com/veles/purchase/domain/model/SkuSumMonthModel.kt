package com.veles.purchase.domain.model

import java.time.LocalDateTime

data class SkuSumMonthModel(
    val skuSumMonth: String?,
    val skuMonth: String?,
    val skuLocalData: LocalDateTime?,
    val skuCurrencyCode: String
)
