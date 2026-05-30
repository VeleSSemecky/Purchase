package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class DeletePurchaseUseCase(private val purchaseRepository: PurchaseRepository, private val historyRepository: HistoryRepository) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            purchaseRepository.deletePurchase(
                purchaseModel.createId,
                purchaseCollectionId
            )

            historyRepository.insert(
                purchaseModel.createPurchaseTable(
                    HistoryType.DELETE,
                    purchaseCollectionId
                )
            )
        }
    }
}
