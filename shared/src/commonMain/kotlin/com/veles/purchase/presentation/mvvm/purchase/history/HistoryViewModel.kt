package com.veles.purchase.presentation.mvvm.purchase.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.history.PurchaseHistoryModel
import com.veles.purchase.domain.repository.history.HistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for History Screen
 *
 * Migrated from: HistoryComposeViewModel.kt
 *
 * Manages:
 * - Loading purchase history for a collection
 * - Displaying historical events (add, check, modify, delete)
 * - Showing timestamps for each event
 *
 * Phase 2.10 - History screen migration
 */
class HistoryViewModel(
    private val collectionId: String,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    /**
     * History list for the collection
     * Sorted by timestamp descending (most recent first)
     */
    val historyList: StateFlow<List<PurchaseHistoryModel>> = historyRepository
        .getHistory(collectionId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}