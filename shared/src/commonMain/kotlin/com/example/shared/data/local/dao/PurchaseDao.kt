package com.example.shared.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shared.data.local.entity.PurchaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchase_table ORDER BY time DESC")
    fun getAllPurchases(): Flow<List<PurchaseEntity>>

    @Query("SELECT * FROM purchase_table WHERE id = :id")
    suspend fun getPurchaseById(id: String): PurchaseEntity?

    @Query("SELECT * FROM purchase_table WHERE collection_id = :collectionId ORDER BY time DESC")
    fun getPurchasesByCollection(collectionId: String): Flow<List<PurchaseEntity>>

    @Query("SELECT * FROM purchase_table WHERE is_checked = :isChecked ORDER BY time DESC")
    fun getPurchasesByStatus(isChecked: Boolean): Flow<List<PurchaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchases(purchases: List<PurchaseEntity>)

    @Update
    suspend fun updatePurchase(purchase: PurchaseEntity)

    @Delete
    suspend fun deletePurchase(purchase: PurchaseEntity)

    @Query("DELETE FROM purchase_table WHERE id = :id")
    suspend fun deletePurchaseById(id: String)

    @Query("DELETE FROM purchase_table WHERE collection_id = :collectionId")
    suspend fun deletePurchasesByCollection(collectionId: String)

    @Query("DELETE FROM purchase_table")
    suspend fun deleteAllPurchases()

    @Query("SELECT COUNT(*) FROM purchase_table")
    suspend fun getPurchaseCount(): Int

    @Query("SELECT COUNT(*) FROM purchase_table WHERE is_checked = 1")
    suspend fun getCheckedPurchaseCount(): Int
}
