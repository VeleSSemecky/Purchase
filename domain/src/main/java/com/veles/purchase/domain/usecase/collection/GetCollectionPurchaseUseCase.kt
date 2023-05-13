package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository
import javax.inject.Inject

class GetCollectionPurchaseUseCase @Inject constructor(
    private val getCollectionPurchaseRepository: GetCollectionPurchaseRepository
) {

    suspend operator fun invoke(id: String): PurchaseCollectionModel =
        getCollectionPurchaseRepository.getCollectionPurchase(id)
}
