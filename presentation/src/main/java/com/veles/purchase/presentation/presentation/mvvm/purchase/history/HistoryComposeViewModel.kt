package com.veles.purchase.presentation.presentation.mvvm.purchase.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.usecase.purchase.GetPurchaseHistoryUseCase
import com.veles.purchase.presentation.model.purchase.PurchaseTableModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseTableModelUI
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryComposeViewModel(
    val savedStateHandle: SavedStateHandle,
    private val getPurchaseHistoryUseCase: GetPurchaseHistoryUseCase
) : ViewModel() {

    private val args: HistoryComposeFragmentArgs =
        HistoryComposeFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val stateFlowListHistory: StateFlow<List<PurchaseTableModelUI>> by lazy {
        getPurchaseHistory()
    }

    private fun getPurchaseHistory() =
        getPurchaseHistoryUseCase(args.modelCollectionPurchase.id)
            .map { list ->
                list.map { it.toPurchaseTableModelUI() }
            }.stateIn(
                viewModelScope,
                started = WhileSubscribed(),
                initialValue = emptyList()
            )
}
