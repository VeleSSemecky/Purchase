package com.veles.purchase.domain.repository.history

import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.history.PurchaseHistoryModel
import com.veles.purchase.domain.util.TimeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Mock implementation of HistoryRepository
 *
 * Stores history in memory with some pre-populated mock data
 */
class MockHistoryRepository : HistoryRepository {

    private val historyFlow = MutableStateFlow<List<PurchaseHistoryModel>>(generateMockHistory())

    override fun getHistory(collectionId: String): Flow<List<PurchaseHistoryModel>> {
        return historyFlow.map { list ->
            list.filter { it.collectionId == collectionId }
                .sortedByDescending { it.timestamp }
        }
    }

    override suspend fun addHistory(history: PurchaseHistoryModel) {
        historyFlow.value = historyFlow.value + history
    }

    override suspend fun clearHistory(collectionId: String) {
        historyFlow.value = historyFlow.value.filterNot { it.collectionId == collectionId }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun generateMockHistory(): List<PurchaseHistoryModel> {
        val now = TimeProvider.currentTimeMillis()
        val oneHour = 60 * 60 * 1000L
        val oneDay = 24 * oneHour

        return listOf(
            // Recent history (today)
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_1",
                purchaseName = "Milk",
                purchaseComment = "2 liters",
                isChecked = true,
                hasImages = false,
                historyType = HistoryType.CHECK,
                timestamp = now - (1 * oneHour),
                collectionId = "test_collection_1"
            ),
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_2",
                purchaseName = "Bread",
                purchaseComment = "Whole wheat",
                isChecked = false,
                hasImages = true,
                historyType = HistoryType.ADD,
                timestamp = now - (2 * oneHour),
                collectionId = "test_collection_1"
            ),
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_3",
                purchaseName = "Eggs",
                purchaseComment = "12 pack",
                isChecked = true,
                hasImages = false,
                historyType = HistoryType.CHECK,
                timestamp = now - (3 * oneHour),
                collectionId = "test_collection_1"
            ),

            // Yesterday
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_4",
                purchaseName = "Tomatoes",
                purchaseComment = "1 kg",
                isChecked = false,
                hasImages = false,
                historyType = HistoryType.CHANGE,
                timestamp = now - (1 * oneDay),
                collectionId = "test_collection_1"
            ),
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_5",
                purchaseName = "Cheese",
                purchaseComment = "Cheddar",
                isChecked = true,
                hasImages = false,
                historyType = HistoryType.CHECK,
                timestamp = now - (1 * oneDay + 2 * oneHour),
                collectionId = "test_collection_1"
            ),

            // Last week
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_6",
                purchaseName = "Chicken",
                purchaseComment = "1.5 kg",
                isChecked = false,
                hasImages = true,
                historyType = HistoryType.DELETE,
                timestamp = now - (5 * oneDay),
                collectionId = "test_collection_1"
            ),
            PurchaseHistoryModel(
                id = Uuid.random().toString(),
                purchaseId = "purchase_7",
                purchaseName = "Rice",
                purchaseComment = "2 kg bag",
                isChecked = true,
                hasImages = false,
                historyType = HistoryType.UNCHECK,
                timestamp = now - (6 * oneDay),
                collectionId = "test_collection_1"
            )
        )
    }
}
