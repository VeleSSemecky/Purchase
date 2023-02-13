package com.veles.purchase.presentation.presentation.mvvm.purchase.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.usecase.purchase.FirebasePurchaseGetHistoryUseCase
import com.veles.purchase.presentation.model.purchase.PurchaseTableModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseTableModelUI
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HistoryComposeViewModel @Inject constructor(
    private val firebasePurchaseGetHistoryUseCase: FirebasePurchaseGetHistoryUseCase
) : ViewModel() {

    val stateFlowListHistory: MutableStateFlow<List<PurchaseTableModelUI>> =
        MutableStateFlow(emptyList())

    init {
        getHistory()
    }

    private fun getHistory() = viewModelScope.launch {
        stateFlowListHistory.emit(
            firebasePurchaseGetHistoryUseCase().map {
                it.toPurchaseTableModelUI()
            }
        )
    }
}
