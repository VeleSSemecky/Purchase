package com.example.shared.domain.repository

import com.example.shared.domain.model.purchase.PurchaseModel
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {

    fun getAllPurchases(): Flow<List<PurchaseModel>>

    suspend fun getPurchaseById(id: String): PurchaseModel?

    fun getPurchasesByCollection(collectionId: String): Flow<List<PurchaseModel>>

    fun getPurchasesByStatus(isChecked: Boolean): Flow<List<PurchaseModel>>

    suspend fun insertPurchase(purchase: PurchaseModel)

    suspend fun insertPurchases(purchases: List<PurchaseModel>)

    suspend fun updatePurchase(purchase: PurchaseModel)

    suspend fun deletePurchase(purchase: PurchaseModel)

    suspend fun deletePurchaseById(id: String)

    suspend fun deletePurchasesByCollection(collectionId: String)

    suspend fun deleteAllPurchases()

    suspend fun getPurchaseCount(): Int

    suspend fun getCheckedPurchaseCount(): Int
}
