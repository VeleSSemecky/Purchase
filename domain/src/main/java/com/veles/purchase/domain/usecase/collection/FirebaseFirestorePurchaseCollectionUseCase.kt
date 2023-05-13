package com.veles.purchase.domain.usecase.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.repository.collection.GetCollectionPurchaseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FirebaseFirestorePurchaseCollectionUseCase @Inject constructor(
    private val getCollectionPurchaseRepository: GetCollectionPurchaseRepository
) {

    suspend operator fun invoke(id: String?): PurchaseCollectionModel? =
        when (id) {
            null -> null
            else -> getCollectionPurchaseRepository.getCollectionPurchase(id)
        }

    suspend operator fun invoke(): Flow<List<PurchaseCollectionModel>> =
        getCollectionPurchaseRepository.getListCollectionPurchases()
}
