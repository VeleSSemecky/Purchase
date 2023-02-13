package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.firebase.remove.FirebaseRemoveRepository
import com.veles.purchase.domain.repository.firebase.storage.FirebaseStorageRepository
import com.veles.purchase.domain.repository.history.HistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

class DeletePurchaseUseCase @Inject constructor(
    private val firebaseRemoveRepository: FirebaseRemoveRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository,
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
        firebaseRemoveRepository.apiFirebaseRemove(
            purchaseModel.createId,
            purchaseCollectionId
        )
    }

    private suspend fun removeAsyncFirebaseStorage(
        purchaseModel: PurchaseModel
    ) = withContext(currentCoroutineContext()) {
        if (purchaseModel.listImage.any { it.status == PhotoStatus.DOWNLOADED }) {
            firebaseStorageRepository.apiFirebaseStorageDelete(purchaseModel.createId)
        }
    }

    private suspend fun removeAsyncPurchase(
        purchaseModel: PurchaseModel
    ) = withContext(currentCoroutineContext()) {
        historyRepository.insert(purchaseModel.createPurchaseTable(HistoryType.DELETE))
    }
}
