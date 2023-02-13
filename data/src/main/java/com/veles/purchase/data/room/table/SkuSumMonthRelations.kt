package com.veles.purchase.data.room.table

import android.icu.util.Currency
import androidx.room.ColumnInfo
import com.veles.purchase.domain.model.SkuSumMonthModel
import java.time.LocalDateTime
import java.util.Locale

data class SkuSumMonthRelations(
    @ColumnInfo(name = "SkuSumMonth")
    val skuSumMonth: String?,
    @ColumnInfo(name = "SkuMonth")
    val skuMonth: String?,
    @ColumnInfo(name = "SkuLocalData")
    val skuLocalData: LocalDateTime?,
    @ColumnInfo(name = "SkuCurrencyCode")
    val skuCurrencyCode: String = Currency.getInstance(Locale.getDefault()).currencyCode
)

fun SkuSumMonthRelations.toSkuSumMonthModel() = SkuSumMonthModel(
    skuSumMonth = skuSumMonth,
    skuMonth = skuMonth,
    skuLocalData = skuLocalData,
    skuCurrencyCode = skuCurrencyCode
)
