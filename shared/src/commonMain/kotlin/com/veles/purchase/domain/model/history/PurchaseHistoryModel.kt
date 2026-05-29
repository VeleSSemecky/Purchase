package com.veles.purchase.domain.model.history

import com.veles.purchase.domain.model.purchase.PurchaseTableModel

/**
 * Purchase history model for tracking changes
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
)

/**
 * Mapper extensions for PurchaseHistoryModel
 */

/**
 * Convert PurchaseTableModel to PurchaseHistoryModel
 */
fun PurchaseTableModel.toHistoryModel(): PurchaseHistoryModel = PurchaseHistoryModel(
    id = id,
    purchaseId = id,
    purchaseName = text,
    purchaseComment = "", // Not stored in PurchaseTableModel
    isChecked = check,
    hasImages = false, // Not tracked in PurchaseTableModel yet
    historyType = typeHistory,
    timestamp = time,
    collectionId = collectionId
)

/**
 * Convert list of PurchaseTableModel to list of PurchaseHistoryModel
 */
fun List<PurchaseTableModel>.toHistoryModels(): List<PurchaseHistoryModel> = map { it.toHistoryModel() }
