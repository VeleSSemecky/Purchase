package com.veles.purchase.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.veles.purchase.data.room.table.SkuPhotoEntity

@Dao
interface SkuPhotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SkuPhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<SkuPhotoEntity>)

    @Query("SELECT * FROM SkuPhotoEntity WHERE SkuId=:skuId")
    suspend fun getSkuPhotoEntityList(skuId: String): List<SkuPhotoEntity>

    @Query("SELECT * FROM SkuPhotoEntity WHERE SkuPhotoId=:skuPhotoId")
    suspend fun getSkuPhotoEntity(skuPhotoId: String): SkuPhotoEntity?

    @Query("DELETE FROM SkuPhotoEntity WHERE SkuPhotoId=:skuPhotoId")
    suspend fun deleteSkuPhotoEntityWithPhotoId(skuPhotoId: String)

    @Query("DELETE FROM SkuPhotoEntity WHERE SkuId=:skuId")
    suspend fun deleteSkuPhotoEntityWithSkuId(skuId: String)
}
