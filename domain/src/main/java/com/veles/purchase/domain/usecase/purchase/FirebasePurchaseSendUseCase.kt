package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.firebase.set.FirebaseRepository
import javax.inject.Inject

class FirebasePurchaseSendUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ): Boolean = firebaseRepository.apiFirebaseFirestore(
        purchaseModel,
        purchaseCollectionId
    )
}
