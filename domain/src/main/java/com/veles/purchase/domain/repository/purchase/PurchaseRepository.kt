package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {

    fun getFlowPurchase(purchaseCollectionId: String, search: String): Flow<List<PurchaseModel>>
    suspend fun getPurchase(collectionPurchaseId: String, purchaseId: String): PurchaseModel
}
