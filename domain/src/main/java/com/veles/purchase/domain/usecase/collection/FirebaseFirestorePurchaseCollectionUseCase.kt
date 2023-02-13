package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.get.FirebaseCollectionPurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FirebaseFirestorePurchaseCollectionUseCase @Inject constructor(
    private val firebaseCollectionPurchaseRepository: FirebaseCollectionPurchaseRepository
) {

    suspend operator fun invoke(id: String?): PurchaseCollectionModel? =
        if (id == null) {
            null
        } else {
            firebaseCollectionPurchaseRepository.apiFirebaseFirestorePurchase(
                id
            )
        }

    suspend operator fun invoke(): Flow<List<PurchaseCollectionModel>> =
        firebaseCollectionPurchaseRepository.apiFirebaseFirestorePurchase()
}
