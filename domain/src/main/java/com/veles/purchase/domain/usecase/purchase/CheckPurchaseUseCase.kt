package com.veles.purchase.domain.usecase.purchase

import com.veles.purchase.domain.core.dispatcher.AppCoroutineDispatcher
import com.veles.purchase.domain.model.history.toHistoryType
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.createPurchaseTable
import javax.inject.Inject
import kotlinx.coroutines.withContext

class CheckPurchaseUseCase @Inject constructor(
    private val coroutineDispatcher: AppCoroutineDispatcher,
    private val setPurchaseHistoryUseCase: SetPurchaseHistoryUseCase,
    private val firebasePurchaseSendUseCase: FirebasePurchaseSendUseCase
) {

    suspend operator fun invoke(
        purchaseCollectionId: String,
        purchaseModel: PurchaseModel
    ) {
        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            setPurchaseHistoryUseCase(
                purchaseModel.createPurchaseTable(purchaseModel.toHistoryType())
            )
        }
        withContext(coroutineDispatcher.coroutineDispatcherIO()) {
            if (purchaseCollectionId.isEmpty()) return@withContext
            firebasePurchaseSendUseCase(
                purchaseModel.copy(check = purchaseModel.check.not()),
                purchaseCollectionId
            )
        }
    }
}
