package com.veles.purchase.presentation.model.purchase.compose

import com.veles.purchase.presentation.model.sort.SortPurchase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SortPurchaseState(
    val flowSortPurchase: StateFlow<SortPurchase>,
    val onSortClicked: () -> Unit
) {

    companion object {
        val PREVIEW_STATE = SortPurchaseState(
            flowSortPurchase = MutableStateFlow(SortPurchase.SORTING_UNCHECK),
            onSortClicked = { }
        )
    }
}
