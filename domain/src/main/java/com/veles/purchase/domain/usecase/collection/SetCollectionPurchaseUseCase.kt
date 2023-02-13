package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.set.FirebaseSetCollectionPurchaseRepository
import javax.inject.Inject

class SetCollectionPurchaseUseCase @Inject constructor(
    private val firebaseSetCollectionPurchaseRepository: FirebaseSetCollectionPurchaseRepository
) {

    suspend operator fun invoke(purchaseModel: PurchaseCollectionModel): Boolean =
        firebaseSetCollectionPurchaseRepository.apiFirebaseFirestore(purchaseModel)
}
