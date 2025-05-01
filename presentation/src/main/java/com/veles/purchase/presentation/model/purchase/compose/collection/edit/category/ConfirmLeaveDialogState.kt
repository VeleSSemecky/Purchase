package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfirmLeaveDialogState(
    val flowConfirmLeaveDialogType: StateFlow<ConfirmLeaveDialogType>,
    val onConfirmLeaveDismiss: () -> Unit,
    val onConfirmLeaveClicked: () -> Unit,
) {

    companion object {
        val PREVIEW_STATE = ConfirmLeaveDialogState(
            flowConfirmLeaveDialogType = MutableStateFlow(ConfirmLeaveDialogType.Open),
            onConfirmLeaveDismiss = {},
            onConfirmLeaveClicked = {}
        )
    }
}

sealed interface ConfirmLeaveDialogType {
    data object Open : ConfirmLeaveDialogType
    data object Close : ConfirmLeaveDialogType
}
