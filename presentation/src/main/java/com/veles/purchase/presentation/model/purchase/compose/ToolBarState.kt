package com.veles.purchase.presentation.model.purchase.compose

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.utill.emptyString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ToolBarState(
    val flowSearchText: StateFlow<String>,
    val flowCollectionPurchase: StateFlow<PurchaseCollectionModel>,
    val updateSearchText: (text: String) -> Unit,
    val onSettingsClicked: () -> Unit,
    val onBackClicked: () -> Unit
) {

    companion object {
        val PREVIEW_STATE = ToolBarState(
            flowSearchText = MutableStateFlow(emptyString()),
            flowCollectionPurchase = MutableStateFlow(PurchaseCollectionModel.EMPTY),
            updateSearchText = { },
            onSettingsClicked = { },
            onBackClicked = { }
        )
    }
}
