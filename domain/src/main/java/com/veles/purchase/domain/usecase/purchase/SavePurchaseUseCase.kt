package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcher
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.*
import com.veles.purchase.domain.usecase.NotificationMessageUseCase
import com.veles.purchase.domain.usecase.storage.FirebaseStorageUseCase
import com.veles.purchase.domain.usecase.storage.StorageDeleteUseCase
import javax.inject.Inject
import kotlinx.coroutines.withContext

class SavePurchaseUseCase @Inject constructor(
    private val coroutineDispatcher: AppCoroutineDispatcher,
    private val firebasePurchaseSetHistoryUseCase: FirebasePurchaseSetHistoryUseCase,
    private val storageDeleteUseCase: StorageDeleteUseCase,
    private val firebaseStorageUseCase: FirebaseStorageUseCase,
    private val firebasePurchaseSendUseCase: FirebasePurchaseSendUseCase,
    private val notificationMessageUseCase: NotificationMessageUseCase
) {

    suspend operator fun invoke(
        modelCollectionPurchase: PurchaseCollectionModel?,
        listPurchasePhotoModel: List<PurchasePhotoModel>,
        purchaseModel: PurchaseModel,
        isNewPurchase: Boolean
    ) = withContext(coroutineDispatcher.coroutineDispatcherIO()) {
        if (modelCollectionPurchase == null) return@withContext

        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            val historyType = if (isNewPurchase) HistoryType.ADD else HistoryType.CHANGE
            firebasePurchaseSetHistoryUseCase(purchaseModel.createPurchaseTable(historyType))
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
                modelCollectionPurchase.id
            )
        }

        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            if (isNewPurchase) {
                notificationMessageUseCase.apiNotificationMessage(
                    modelCollectionPurchase.id,
                    purchaseModel.text
                )
            }
        }
    }
}
