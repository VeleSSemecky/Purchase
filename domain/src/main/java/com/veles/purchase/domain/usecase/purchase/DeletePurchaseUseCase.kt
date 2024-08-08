package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.purchase.PurchaseRepository
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import com.veles.purchase.domain.repository.storage.DeletePurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

class DeletePurchaseUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
    private val deletePurchasePhotoRepository: DeletePurchasePhotoRepository,
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ): Boolean = withContext(Dispatchers.IO) {
        removeAsyncPurchase(purchaseModel, purchaseCollectionId)
        removeAsyncFirebaseStorage(purchaseModel)
        removeAsyncPurchase(purchaseModel)
        true
    }

    private suspend fun removeAsyncPurchase(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = withContext(currentCoroutineContext()) {
        purchaseRepository.deletePurchase(
            purchaseModel.createId,
            purchaseCollectionId
        )
    }

    private suspend fun removeAsyncFirebaseStorage(
        purchaseModel: PurchaseModel
    ) = withContext(currentCoroutineContext()) {
        deletePurchasePhotoRepository.deletePhotos(purchaseModel.createId, purchaseModel.listImage)
    }

    private suspend fun removeAsyncPurchase(
        purchaseModel: PurchaseModel
    ) = withContext(currentCoroutineContext()) {
        historyRepository.insert(purchaseModel.createPurchaseTable(HistoryType.DELETE))
    }
}
