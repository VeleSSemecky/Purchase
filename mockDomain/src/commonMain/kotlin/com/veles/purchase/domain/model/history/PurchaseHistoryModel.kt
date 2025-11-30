package com.veles.purchase.domain.model.history

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Purchase history model for tracking changes
 *
 * Original: PurchaseTableModel
 * Adapted for KMP
 *
 * Represents a historical event for a purchase (created, checked, modified, deleted)
 */
data class PurchaseHistoryModel(
    val id: String,
    val purchaseId: String,
    val purchaseName: String,
    val purchaseComment: String,
    val isChecked: Boolean,
    val hasImages: Boolean,
    val historyType: HistoryType,
    val timestamp: Long, // Unix timestamp in milliseconds
    val collectionId: String
) {
    companion object {
        @OptIn(ExperimentalUuidApi::class)
        val EMPTY = PurchaseHistoryModel(
            id = "",
            purchaseId = "",
            purchaseName = "",
            purchaseComment = "",
            isChecked = false,
            hasImages = false,
            historyType = HistoryType.ADD,
            timestamp = 0L,
            collectionId = ""
        )

        @OptIn(ExperimentalUuidApi::class)
        fun createMockHistory(
            purchaseId: String,
            purchaseName: String,
            historyType: HistoryType,
            isChecked: Boolean = false,
            timestamp: Long = System.currentTimeMillis(),
            collectionId: String = ""
        ) = PurchaseHistoryModel(
            id = Uuid.random().toString(),
            purchaseId = purchaseId,
            purchaseName = purchaseName,
            purchaseComment = "",
            isChecked = isChecked,
            hasImages = false,
            historyType = historyType,
            timestamp = timestamp,
            collectionId = collectionId
        )
    }
}

/**
 * History type enum
 */
enum class HistoryType {
    CHECK,      // Purchase was checked
    ADD,        // Purchase was added
    CHANGE,     // Purchase was modified
    DELETE,     // Purchase was deleted
    UNCHECK     // Purchase was unchecked
}