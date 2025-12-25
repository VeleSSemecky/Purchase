@file:OptIn(ExperimentalTime::class)

package com.veles.purchase.data.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.utill.emptyString
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@Entity
data class SkuEntity(
    @PrimaryKey
    @ColumnInfo(name = "SkuId")
    val skuId: String = createPrimaryIDKey(),
    @ColumnInfo(name = "SkuLocalData")
    val skuLocalData: LocalDateTime = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    @ColumnInfo(name = "SkuName")
    val skuName: String = emptyString(),
    @ColumnInfo(name = "SkuComment")
    val skuComment: String = emptyString(),
    @ColumnInfo(name = "SkuPrice")
    val skuPrice: String = emptyString(),
    @ColumnInfo(name = "SkuCurrencyCode")
    val skuCurrencyCode: String = "USD"  // Default currency, can be changed by user
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

// TODO: Re-implement these extension functions with kotlinx.datetime
// Or move to presentation layer where they're actually used
