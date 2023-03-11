package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcher
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.usecase.NotificationMessageUseCase
import com.veles.purchase.domain.usecase.price.PriceUseCase
import com.veles.purchase.domain.usecase.storage.FirebaseStorageUseCase
import com.veles.purchase.domain.usecase.storage.StorageDeleteUseCase
import javax.inject.Inject
import kotlinx.coroutines.withContext

class SavePurchaseUseCase @Inject constructor(
    private val coroutineDispatcher: AppCoroutineDispatcher,
    private val setPurchaseHistoryUseCase: SetPurchaseHistoryUseCase,
    private val storageDeleteUseCase: StorageDeleteUseCase,
    private val firebaseStorageUseCase: FirebaseStorageUseCase,
    private val firebasePurchaseSendUseCase: FirebasePurchaseSendUseCase,
    private val notificationMessageUseCase: NotificationMessageUseCase,
    private val priceUseCase: PriceUseCase
) {

    suspend operator fun invoke(
        purchaseCollectionId: String,
        listPurchasePhotoModel: List<PurchasePhotoModel>,
        editPurchaseModel: PurchaseModel,
        isNewPurchase: Boolean
    ) = withContext(coroutineDispatcher.coroutineDispatcherIO()) {
        if (purchaseCollectionId.isEmpty()) return@withContext
        val purchaseModel = editPurchaseModel.copy(
            price = priceUseCase(editPurchaseModel.price)
        )
        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            val historyType = if (isNewPurchase) HistoryType.ADD else HistoryType.CHANGE
            setPurchaseHistoryUseCase(purchaseModel.createPurchaseTable(historyType))
        }

        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            listPurchasePhotoModel.forEach {
                storageDeleteUseCase.deleteStorage(it)
            }
        }

        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            firebaseStorageUseCase.apiFirebaseStorage(
                purchaseModel.getLocalListPurchasePhotoModel()
            )
        }

        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            firebasePurchaseSendUseCase(
                purchaseModel.copy(
                    listImage = purchaseModel.listImage.map {
                        it.copy(status = PhotoStatus.DOWNLOADED)
                    }
                ),
                purchaseCollectionId
            )
        }

        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            if (isNewPurchase) {
                notificationMessageUseCase.apiNotificationMessage(
                    purchaseCollectionId,
                    purchaseModel.text
                )
            }
        }
    }
}
