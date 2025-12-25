package com.example.shared.domain.usecase

import com.example.shared.domain.model.purchase.PurchaseModel
import com.example.shared.domain.model.purchase.generateId
import com.example.shared.domain.repository.PurchaseRepository
import com.veles.purchase.domain.util.TimeProvider
import kotlinx.coroutines.flow.Flow

class GetAllPurchasesUseCase(
    private val repository: PurchaseRepository
) {
    operator fun invoke(): Flow<List<PurchaseModel>> {
        return repository.getAllPurchases()
    }
}

class AddPurchaseUseCase(
    private val repository: PurchaseRepository
) {
    suspend operator fun invoke(
        text: String,
        count: String,
        price: String = "",
        collectionId: String = ""
    ): PurchaseModel {
        val purchase = PurchaseModel(
            id = generateId(),
            text = text,
            count = count,
            isChecked = false,
            time = TimeProvider.currentTimeMillis(),
            price = price,
            collectionId = collectionId
        )
        repository.insertPurchase(purchase)
        return purchase
    }
}

class UpdatePurchaseStatusUseCase(
    private val repository: PurchaseRepository
) {
    suspend operator fun invoke(purchase: PurchaseModel, isChecked: Boolean) {
        val updatedPurchase = purchase.copy(isChecked = isChecked)
        repository.updatePurchase(updatedPurchase)
    }
}

class DeletePurchaseUseCase(
    private val repository: PurchaseRepository
) {
    suspend operator fun invoke(purchaseId: String) {
        repository.deletePurchaseById(purchaseId)
    }
}
