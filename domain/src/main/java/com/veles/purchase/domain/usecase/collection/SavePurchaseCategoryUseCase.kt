package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.purchase.PurchaseCategoryRepository
import javax.inject.Inject

class SavePurchaseCategoryUseCase @Inject constructor(
    private val purchaseCategoryRepository: PurchaseCategoryRepository
) {

    suspend operator fun invoke(
        purchaseCollectionModel: PurchaseCollectionModel,
        newPurchaseCategoryModel: List<PurchaseCategoryModel>
    ) = runCatching {
        purchaseCategoryRepository.setPurchaseCategory(
            purchaseCollectionModel.copy(
                categoryModels = newPurchaseCategoryModel
            )
        )
    }
}
