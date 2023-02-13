package com.veles.purchase.data.room.table

import android.icu.util.Currency
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.utill.emptyString
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.util.Locale

@Entity
data class SkuEntity(
    @PrimaryKey
    @ColumnInfo(name = "SkuId")
    val skuId: String = createPrimaryIDKey(),
    @ColumnInfo(name = "SkuLocalData")
    val skuLocalData: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "SkuName")
    val skuName: String = emptyString(),
    @ColumnInfo(name = "SkuComment")
    val skuComment: String = emptyString(),
    @ColumnInfo(name = "SkuPrice")
    val skuPrice: String = emptyString(),
    @ColumnInfo(name = "SkuCurrencyCode")
    val skuCurrencyCode: String = Currency.getInstance(Locale.getDefault()).currencyCode
)

fun SkuEntity.toSkuModel(): SkuModel =
    SkuModel(
        skuId = skuId,
        skuLocalData = skuLocalData,
        skuName = skuName,
        skuComment = skuComment,
        skuPrice = skuPrice,
        skuCurrencyCode = skuCurrencyCode
    )

fun SkuModel.toSkuEntity(): SkuEntity =
    SkuEntity(
        skuId = skuId,
        skuLocalData = skuLocalData,
        skuName = skuName,
        skuComment = skuComment,
        skuPrice = skuPrice,
        skuCurrencyCode = skuCurrencyCode
    )

fun List<SkuModel>.groupList(): Map<Pair<Month, Int>, List<SkuModel>> {
    val yearComparator = compareByDescending<Pair<Month, Int>> { it.second }
    val monthComparator = yearComparator.thenByDescending { it.first }
    return groupBy { Pair(it.skuLocalData.month, it.skuLocalData.year) }
        .toSortedMap(monthComparator)
}

fun List<SkuModel>.sortedByDescendingLocalDateTime() =
    this.sortedByDescending {
        it.skuLocalData.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
