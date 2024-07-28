package com.veles.purchase.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.veles.purchase.data.room.table.PurchaseTable

@Dao
interface PurchaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg purchaseTables: PurchaseTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(purchaseTables: List<PurchaseTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(purchaseTable: PurchaseTable): Long?

    @Query("SELECT * FROM PurchaseTable ORDER BY time DESC")
    suspend fun getListPurchaseTables(): List<PurchaseTable>

    @Update
    suspend fun update(purchaseTable: PurchaseTable)

    @Delete
    suspend fun delete(purchaseTable: PurchaseTable): Int

    @Delete
    suspend fun delete(purchaseTables: List<PurchaseTable>)
}
