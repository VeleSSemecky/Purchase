package com.veles.purchase.presentation.model.purchase.compose.history

import com.veles.purchase.presentation.model.purchase.PurchaseTableModelUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HistoryComposeContentState(
    val flowListPurchaseTableModelUI: StateFlow<List<PurchaseTableModelUI>>,
    val toolBarState: HistoryToolBarState
) {

    companion object {

        val PREVIEW_STATE = HistoryComposeContentState(
            flowListPurchaseTableModelUI = MutableStateFlow(
                listOf(
                    PurchaseTableModelUI.PREVIEW_STATE
                )
            ),
            toolBarState = HistoryToolBarState.PREVIEW_STATE
        )
    }
}
