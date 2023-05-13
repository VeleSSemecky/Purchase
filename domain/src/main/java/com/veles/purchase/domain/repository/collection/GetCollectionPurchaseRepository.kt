package com.veles.purchase.domain.repository.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import kotlinx.coroutines.flow.Flow

interface GetCollectionPurchaseRepository {

    suspend fun getListCollectionPurchases(): Flow<List<PurchaseCollectionModel>>

    suspend fun getCollectionPurchase(id: String): PurchaseCollectionModel
}
