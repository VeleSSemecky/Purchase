package com.veles.purchase.domain.repository.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.user.UserPurchaseModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Mock implementation of CollectionRepository for development and testing
 *
 * Provides in-memory storage with reactive Flow-based data
 * Simulates network delays to mimic real API behavior
 */
class MockCollectionRepository : CollectionRepository {

    @OptIn(ExperimentalUuidApi::class)
    private val _collections = MutableStateFlow(
        listOf(
            PurchaseCollectionModel(
                id = "collection_1",
                name = "Groceries 🛒",
                creator = UserPurchaseModel.MOCK_USER,
                listMembers = listOf("mock_user_123"),
                categoryModels = listOf(
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Fruits"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Vegetables"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Dairy"
                    )
                )
            ),
            PurchaseCollectionModel(
                id = "collection_2",
                name = "Home Supplies 🏠",
                creator = UserPurchaseModel.MOCK_USER,
                listMembers = listOf("mock_user_123", "mock_user_456"),
                categoryModels = listOf(
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Cleaning"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Tools"
                    )
                )
            ),
            PurchaseCollectionModel(
                id = "collection_3",
                name = "Electronics 💻",
                creator = UserPurchaseModel.MOCK_USER,
                listMembers = listOf("mock_user_123"),
                categoryModels = listOf(
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Computers"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Accessories"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Mobile"
                    )
                )
            ),
            PurchaseCollectionModel(
                id = "collection_4",
                name = "Books 📚",
                creator = UserPurchaseModel.MOCK_USER,
                listMembers = listOf("mock_user_123", "mock_user_789"),
                categoryModels = listOf(
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Fiction"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Technical"
                    )
                )
            ),
            PurchaseCollectionModel(
                id = "collection_5",
                name = "Monthly Budget 💰",
                creator = UserPurchaseModel.MOCK_USER,
                listMembers = listOf("mock_user_123", "mock_user_456", "mock_user_789"),
                categoryModels = listOf(
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Food"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Transport"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Entertainment"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Utilities"
                    )
                )
            ),
            PurchaseCollectionModel(
                id = "collection_6",
                name = "Health & Fitness 💪",
                creator = UserPurchaseModel.MOCK_USER,
                listMembers = listOf("mock_user_123"),
                categoryModels = listOf(
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Supplements"
                    ),
                    PurchaseCategoryModel(
                        id = Uuid.random().toString(),
                        name = "Equipment"
                    )
                )
            )
        )
    )

    override fun getCollections(): Flow<List<PurchaseCollectionModel>> {
        return _collections.asStateFlow()
    }

    override suspend fun getCollection(collectionId: String): PurchaseCollectionModel? {
        delay(200) // Simulate network delay
        return _collections.value.find { it.id == collectionId }
    }

    override suspend fun saveCollection(collection: PurchaseCollectionModel) {
        delay(300) // Simulate network delay

        val currentList = _collections.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == collection.id }

        if (index != -1) {
            // Update existing
            currentList[index] = collection
        } else {
            // Add new
            currentList.add(collection)
        }

        _collections.value = currentList
    }

    override suspend fun deleteCollection(collection: PurchaseCollectionModel) {
        delay(200) // Simulate network delay

        val currentList = _collections.value.toMutableList()
        currentList.removeAll { it.id == collection.id }
        _collections.value = currentList
    }
}