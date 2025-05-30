package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.repository.purchase.PurchaseCategoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCollectionPurchaseCategoryUseCase @Inject constructor(
    private val purchaseCategoryRepository: PurchaseCategoryRepository
) {

    operator fun invoke(purchaseCollectionId: String): Flow<List<PurchaseCategoryModel>> =
        purchaseCategoryRepository.getPurchaseCategory(purchaseCollectionId)
}
