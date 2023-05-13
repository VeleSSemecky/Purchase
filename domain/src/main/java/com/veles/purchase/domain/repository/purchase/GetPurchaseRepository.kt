package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import kotlinx.coroutines.flow.Flow

interface GetPurchaseRepository {

    fun getLisPurchases(collectionId: String, search: String): Flow<List<PurchaseModel>>

    suspend fun getPurchase(collectionId: String, purchaseId: String): PurchaseModel
}
