package com.veles.purchase.presentation.model.purchase.compose.list

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreatePurchaseState(
    val flowNewNamePurchase: StateFlow<String>,
    val onNewNamePurchaseChanged: (text: String) -> Unit,
    val insertAdd: (text: String) -> Unit
) {

    companion object {
        val PREVIEW_STATE = CreatePurchaseState(
            flowNewNamePurchase = MutableStateFlow("Test name"),
            onNewNamePurchaseChanged = { },
            insertAdd = { }
        )
    }
}
