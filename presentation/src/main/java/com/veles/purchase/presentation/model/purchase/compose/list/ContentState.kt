package com.veles.purchase.presentation.model.purchase.compose.list

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.presentation.model.purchase.PurchaseModelUI
import com.veles.purchase.presentation.model.purchase.compose.core.ProgressState
import com.veles.purchase.presentation.model.purchase.toPurchaseModelUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ContentState(
    val flowListPurchaseModels: StateFlow<List<PurchaseModel>>,
    val apiFirebaseRemoveRepository: (item: PurchaseModel) -> Unit,
    val itemPurchaseState: ItemPurchaseState,
    val createPurchaseState: CreatePurchaseState,
    val sortPurchaseState: SortPurchaseState,
    val progressState: ProgressState,
    val toolBarState: ToolBarState
) {

    companion object {
        val PREVIEW_STATE = ContentState(
            flowListPurchaseModels = MutableStateFlow(listOf(PurchaseModel.TEST)),
            apiFirebaseRemoveRepository = { },
            itemPurchaseState = ItemPurchaseState.PREVIEW_STATE,
            createPurchaseState = CreatePurchaseState.PREVIEW_STATE,
            sortPurchaseState = SortPurchaseState.PREVIEW_STATE,
            progressState = ProgressState.PREVIEW_STATE,
            toolBarState = ToolBarState.PREVIEW_STATE
        )
    }
}
