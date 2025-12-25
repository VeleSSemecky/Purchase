package com.veles.purchase.data.room.table

import androidx.room.ColumnInfo
import com.veles.purchase.domain.model.SkuSumMonthModel
import kotlinx.datetime.LocalDateTime

data class SkuSumMonthRelations(
    @ColumnInfo(name = "SkuSumMonth")
    val skuSumMonth: String?,
    @ColumnInfo(name = "SkuMonth")
    val skuMonth: String?,
    @ColumnInfo(name = "SkuLocalData")
    val skuLocalData: LocalDateTime?,
    @ColumnInfo(name = "SkuCurrencyCode")
    val skuCurrencyCode: String = "USD"  // Default currency
)

fun SkuSumMonthRelations.toSkuSumMonthModel() = SkuSumMonthModel(
    skuSumMonth = skuSumMonth,
    skuMonth = skuMonth,
    skuLocalData = skuLocalData,
    skuCurrencyCode = skuCurrencyCode
)
