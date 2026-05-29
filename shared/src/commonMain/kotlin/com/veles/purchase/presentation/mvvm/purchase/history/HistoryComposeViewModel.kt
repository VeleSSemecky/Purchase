package com.veles.purchase.presentation.mvvm.purchase.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.history.PurchaseHistoryModel
import com.veles.purchase.domain.model.history.toHistoryModels
import com.veles.purchase.domain.usecase.purchase.GetPurchaseHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for History Screen
 * Migrated from presentation module - original name: HistoryComposeViewModel
 */
class HistoryComposeViewModel(private val collectionId: String, private val getPurchaseHistoryUseCase: GetPurchaseHistoryUseCase) : ViewModel() {

    val historyList: StateFlow<List<PurchaseHistoryModel>> = getPurchaseHistoryUseCase(collectionId)
        .map { it.toHistoryModels() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}
