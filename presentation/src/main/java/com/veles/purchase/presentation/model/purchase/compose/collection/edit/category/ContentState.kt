package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI
import com.veles.purchase.presentation.model.purchase.compose.core.ProgressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ContentState(
    val flowListCategory: StateFlow<List<PurchaseCategoryModelUI>>,
    val toolBarState: ToolBarState,
    val progressState: ProgressState,
    val createCategoryState: CreateCategoryState,
    val fabState: FabState,
    val editDialogState: EditDialogState,
    val createCategoryDialogState: CreateCategoryDialogState,
    val confirmLeaveDialogState: ConfirmLeaveDialogState
) {

    companion object {
        val EMPTY_STATE = ContentState(
            flowListCategory = MutableStateFlow(emptyList()),
            toolBarState = ToolBarState.PREVIEW_STATE,
            progressState = ProgressState.PREVIEW_STATE,
            createCategoryState = CreateCategoryState.PREVIEW_STATE,
            fabState = FabState.PREVIEW_STATE,
            editDialogState = EditDialogState.PREVIEW_STATE,
            createCategoryDialogState = CreateCategoryDialogState.PREVIEW_STATE,
            confirmLeaveDialogState = ConfirmLeaveDialogState.PREVIEW_STATE
        )

        val PREVIEW_STATE = ContentState(
            flowListCategory = MutableStateFlow(emptyList()),
            toolBarState = ToolBarState.PREVIEW_STATE,
            progressState = ProgressState.PREVIEW_STATE,
            createCategoryState = CreateCategoryState.PREVIEW_STATE,
            fabState = FabState.PREVIEW_STATE,
            editDialogState = EditDialogState.PREVIEW_STATE,
            createCategoryDialogState = CreateCategoryDialogState.PREVIEW_STATE,
            confirmLeaveDialogState = ConfirmLeaveDialogState.PREVIEW_STATE
        )
    }
}
