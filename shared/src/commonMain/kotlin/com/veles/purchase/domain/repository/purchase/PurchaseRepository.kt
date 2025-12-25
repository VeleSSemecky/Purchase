package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {

    suspend fun getPurchase(collectionId: String, purchaseId: String): PurchaseModel

    suspend fun getSearchPurchaseList(collectionId: String, search: String): List<PurchaseModel>

    fun getPurchaseFlow(collectionId: String): Flow<List<PurchaseModel>>

    suspend fun deletePurchase(purchaseId: String, collectionId: String)

    suspend fun setPurchase(purchaseModel: PurchaseModel, collectionId: String)
}
