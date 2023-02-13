package com.veles.purchase.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.veles.purchase.data.room.table.SkuEntity
import com.veles.purchase.data.room.table.SkuPhotoEntity
import com.veles.purchase.data.room.table.SkuSumMonthRelations
import com.veles.purchase.domain.utill.zeroInt
import com.veles.purchase.domain.utill.zeroString

@Dao
interface SkuDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SkuEntity)

    @Query("SELECT * FROM SkuEntity WHERE SkuId=:skuId")
    suspend fun getSkuEntity(skuId: String): SkuEntity?

    @Query("SELECT * FROM SkuEntity")
    suspend fun getSkuEntityList(): List<SkuEntity>

    @Query(
        "SELECT sum(SkuPrice) SkuSumMonth, strftime('%m', SkuLocalData/1000, 'unixepoch') SkuMonth, SkuCurrencyCode SkuCurrencyCode, SkuLocalData SkuLocalData FROM SkuEntity WHERE strftime('%Y', SkuLocalData/1000, 'unixepoch')=:year GROUP BY SkuCurrencyCode, strftime('%m', SkuLocalData/1000, 'unixepoch')"
    )
    suspend fun getSkuSumMonthListFilterYear(year: String): List<SkuSumMonthRelations>

    @Query(
        "SELECT sum(SkuPrice) SkuSumMonth, strftime('%m', SkuLocalData/1000, 'unixepoch') SkuMonth, SkuCurrencyCode SkuCurrencyCode, SkuLocalData SkuLocalData FROM SkuEntity GROUP BY strftime('%m', SkuLocalData/1000, 'unixepoch')"
    )
    suspend fun getSkuSumMonthList(): List<SkuSumMonthRelations>

    @Query(
        "SELECT sum(SkuPrice) SkuSumMonth, strftime('%m', SkuLocalData/1000, 'unixepoch') SkuMonth, SkuCurrencyCode SkuCurrencyCode, SkuLocalData SkuLocalData FROM SkuEntity WHERE CAST(strftime('%m', SkuLocalData/1000, 'unixepoch') AS INTEGER)=:month GROUP BY SkuCurrencyCode, strftime('%m', SkuLocalData/1000, 'unixepoch')"
    )
    suspend fun getSkuSumMonthListFilterMonth(month: Int): List<SkuSumMonthRelations>

    @Query(
        "SELECT sum(SkuPrice) SkuSumMonth, strftime('%m', SkuLocalData/1000, 'unixepoch') SkuMonth, SkuCurrencyCode SkuCurrencyCode, SkuLocalData SkuLocalData FROM SkuEntity WHERE strftime('%Y', SkuLocalData/1000, 'unixepoch')=:year AND CAST(strftime('%m', SkuLocalData/1000, 'unixepoch') AS INTEGER)=:month GROUP BY SkuCurrencyCode, strftime('%m', SkuLocalData/1000, 'unixepoch')"
    )
    suspend fun getSkuSumMonthListFilterMonthAndYear(
        month: Int,
        year: String
    ): List<SkuSumMonthRelations>

    @Query("DELETE FROM SkuEntity WHERE SkuId=:skuId")
    suspend fun deleteSkuEntity(skuId: String)

    @Query("DELETE FROM SkuPhotoEntity WHERE SkuId=:skuId")
    suspend fun deleteSkuPhotoEntityWithSkuId(skuId: String)

    @Transaction
    suspend fun delete(skuId: String) {
        deleteSkuEntity(skuId)
        deleteSkuPhotoEntityWithSkuId(skuId)
    }

    @Transaction
    suspend fun getSkuSumMonthList(month: Int, year: String) =
        when {
            month != zeroInt() && year != zeroString() -> getSkuSumMonthListFilterMonthAndYear(
                month,
                year
            )
            month != zeroInt() && year == zeroString() -> getSkuSumMonthListFilterMonth(month)
            month == zeroInt() && year != zeroString() -> getSkuSumMonthListFilterYear(year)
            else -> getSkuSumMonthList()
        }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<SkuPhotoEntity>)

    @Transaction
    suspend fun insert(skuEntity: SkuEntity, skuPhotoEntity: List<SkuPhotoEntity> = emptyList()) {
        insert(skuEntity)
        if (skuPhotoEntity.isNotEmpty()) {
            insert(skuPhotoEntity)
        }
    }
}
