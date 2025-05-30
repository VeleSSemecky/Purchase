package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import kotlinx.coroutines.flow.Flow

interface PurchaseCategoryRepository {

    suspend fun setPurchaseCategory(
        purchaseCollection: PurchaseCollectionModel
    )

    fun getPurchaseCategory(purchaseCollectionId: String): Flow<List<PurchaseCategoryModel>>
}
