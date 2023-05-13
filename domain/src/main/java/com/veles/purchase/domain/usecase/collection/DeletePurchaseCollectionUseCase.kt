package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.DeleteCollectionPurchaseRepository
import javax.inject.Inject

class DeletePurchaseCollectionUseCase @Inject constructor(
    private val deleteCollectionPurchaseRepository: DeleteCollectionPurchaseRepository
) {

    suspend operator fun invoke(purchaseCollection: PurchaseCollectionModel) =
        deleteCollectionPurchaseRepository.deletePurchaseCollection(purchaseCollection)
}
