package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.purchase.PurchaseLaterRepository
import com.veles.purchase.domain.repository.storage.DeletePurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoveForLaterPurchaseUseCase @Inject constructor(
    private val purchaseLaterRepository: PurchaseLaterRepository,
    private val deletePurchaseRepository: DeletePurchaseRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = withContext(Dispatchers.IO) {
        deletePurchaseRepository.deletePurchase(
            purchaseModel.createId,
            purchaseCollectionId
        )
    }
}
