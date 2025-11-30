package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.utill.createPrimaryIDKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockPurchaseRepository : PurchaseRepository {

    private val mockPurchases = MutableStateFlow(createMockPurchases())

    override suspend fun getPurchase(collectionId: String, purchaseId: String): PurchaseModel {
        delay(300) // Simulate network delay
        return mockPurchases.value.find { it.createId == purchaseId } ?: PurchaseModel.EMPTY
    }

    override suspend fun getSearchPurchaseList(
        collectionId: String,
        search: String
    ): List<PurchaseModel> {
        delay(200)
        return mockPurchases.value.filter {
            it.text.contains(search, ignoreCase = true)
        }
    }

    override fun getPurchaseFlow(collectionId: String): Flow<List<PurchaseModel>> {
        return mockPurchases.asStateFlow()
    }

    override suspend fun deletePurchase(purchaseId: String, collectionId: String) {
        delay(200)
        mockPurchases.value = mockPurchases.value.filterNot { it.createId == purchaseId }
    }

    override suspend fun setPurchase(purchaseModel: PurchaseModel, collectionId: String) {
        delay(300)
        val currentList = mockPurchases.value.toMutableList()
        val index = currentList.indexOfFirst { it.createId == purchaseModel.createId }
        if (index != -1) {
            currentList[index] = purchaseModel
        } else {
            currentList.add(purchaseModel)
        }
        mockPurchases.value = currentList
    }

    private fun createMockPurchases(): List<PurchaseModel> {
        return listOf(
            PurchaseModel(
                createId = createPrimaryIDKey(),
                text = "Молоко",
                count = "2",
                isChecked = false,
                price = "45.50",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = PurchaseCategoryModel(
                    id = "cat_1",
                    name = "Продукти"
                )
            ),
            PurchaseModel(
                createId = createPrimaryIDKey(),
                text = "Хліб",
                count = "1",
                isChecked = true,
                price = "25.00",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = PurchaseCategoryModel(
                    id = "cat_1",
                    name = "Продукти"
                )
            ),
            PurchaseModel(
                createId = createPrimaryIDKey(),
                text = "Книга 'Kotlin in Action'",
                count = "1",
                isChecked = false,
                price = "850.00",
                userList = emptyList(),
                listImage = emptyList(),
                purchaseCategoryModel = PurchaseCategoryModel(
                    id = "cat_2",
                    name = "Книги"
                )
            ),
            PurchaseModel(
                createId = createPrimaryIDKey(),
                text = "Кава",
                count = "3",
                isChecked = false,
                price = "120.00",
                userList = listOf("user1", "user2"),
                listImage = emptyList(),
                purchaseCategoryModel = PurchaseCategoryModel(
                    id = "cat_1",
                    name = "Продукти"
                )
            ),
            PurchaseModel(
                createId = createPrimaryIDKey(),
                text = "Телефон",
                count = "1",
                isChecked = false,
                price = "15000.00",
                userList = emptyList(),
                listImage = listOf(
                    PurchasePhotoModel(
                        purchaseId = "phone_1",
                        purchasePhotoUri = "https://via.placeholder.com/300"
                    )
                ),
                purchaseCategoryModel = PurchaseCategoryModel(
                    id = "cat_3",
                    name = "Електроніка"
                )
            )
        )
    }
}

