package com.veles.app.functions

import androidx.appfunctions.AppFunctionContext
import androidx.appfunctions.service.AppFunction
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.repository.collection.CollectionRepository
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import com.veles.purchase.domain.utill.zeroString
import com.veles.purchase.platform.logger.AppLogger
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PurchaseAppFunctions : KoinComponent {

    private val savePurchaseUseCase: SavePurchaseUseCase by inject()
    private val collectionRepository: CollectionRepository by inject()

    /**
     * Додає новий товар або продукт до списку покупок користувача.
     * Викликайте цю функцію лише тоді, коли користувач явно просить внести щось до списку,
     * зафіксувати покупку або запланувати придбання товару.
     *
     * @param productName Назва продукту або товару, який потрібно купити (наприклад: "молоко", "яблука").
     * @param quantity Кількість одиниць товару. Якщо користувач не вказав кількість, передавайте 1.
     */
    @OptIn(ExperimentalUuidApi::class)
    @AppFunction(isDescribedByKDoc = true)
    suspend fun addProductToList(
        @Suppress("UNUSED_PARAMETER") context: AppFunctionContext,
        productName: String,
        quantity: Int = 1
    ): Boolean {
        return try {
            val collections = collectionRepository.getCollections().first()
            val collection = collections.firstOrNull() ?: return false
            val collectionId = collection.id

            val purchase = PurchaseModel(
                createId = Uuid.random().toString().uppercase(),
                text = productName,
                count = quantity.toString(),
                isChecked = false,
                price = zeroString(),
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = null
            )

            savePurchaseUseCase(purchase, collectionId)
            true
        } catch (e: Exception) {
            AppLogger.e("PurchaseAppFunctions", "Failed to add product to list", e)
            false
        }
    }
}
