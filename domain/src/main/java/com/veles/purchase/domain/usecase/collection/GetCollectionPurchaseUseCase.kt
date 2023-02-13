package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.get.FirebaseCollectionPurchaseRepository
import javax.inject.Inject

class GetCollectionPurchaseUseCase @Inject constructor(
    private val firebaseCollectionPurchaseRepository: FirebaseCollectionPurchaseRepository
) {

    suspend operator fun invoke(id: String): PurchaseCollectionModel =
        firebaseCollectionPurchaseRepository.apiFirebaseFirestorePurchase(id)
}
