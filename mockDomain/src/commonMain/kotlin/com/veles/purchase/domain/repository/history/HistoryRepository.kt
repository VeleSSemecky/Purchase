package com.veles.purchase.domain.repository.history

import com.veles.purchase.domain.model.history.PurchaseHistoryModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for purchase history
 *
 * Provides access to historical purchase events
 */
interface HistoryRepository {

    /**
     * Get all history for a collection
     */
    fun getHistory(collectionId: String): Flow<List<PurchaseHistoryModel>>

    /**
     * Add a history record
     */
    suspend fun addHistory(history: PurchaseHistoryModel)

    /**
     * Clear all history for a collection
     */
    suspend fun clearHistory(collectionId: String)
}