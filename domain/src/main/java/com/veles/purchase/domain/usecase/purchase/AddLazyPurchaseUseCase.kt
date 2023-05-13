package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcher
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import com.veles.purchase.domain.repository.history.HistoryRepository
import com.veles.purchase.domain.repository.message.NotificationMessageRepository
import com.veles.purchase.domain.repository.purchase.SetPurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

class AddLazyPurchaseUseCase @Inject constructor(
    private val appCoroutineDispatcher: AppCoroutineDispatcher,
    private val setPurchaseRepository: SetPurchaseRepository,
    private val notificationMessageRepository: NotificationMessageRepository,
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = withContext(appCoroutineDispatcher.coroutineDispatcherIO()) {
        sendAsyncOnFirebase(purchaseModel, purchaseCollectionId)
        sendAsyncNotification(purchaseModel, purchaseCollectionId)
        addAsyncInHistory(purchaseModel)
    }

    private suspend fun sendAsyncOnFirebase(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = withContext(currentCoroutineContext()) {
        setPurchaseRepository.setPurchase(
            purchaseModel,
            purchaseCollectionId
        )
    }

    private suspend fun sendAsyncNotification(
        purchaseModel: PurchaseModel,
        purchaseCollectionId: String
    ) = withContext(currentCoroutineContext()) {
        notificationMessageRepository.sendNotificationMessage(
            purchaseCollectionId,
            purchaseModel.text
        )
    }

    private suspend fun addAsyncInHistory(
        purchaseModel: PurchaseModel
    ) = withContext(currentCoroutineContext()) {
        historyRepository.insert(purchaseModel.createPurchaseTable(HistoryType.ADD))
    }
}
