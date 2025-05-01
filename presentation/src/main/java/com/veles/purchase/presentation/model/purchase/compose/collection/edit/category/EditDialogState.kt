package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class EditDialogState(
    val flowEditDialogType: StateFlow<EditDialogType>,
    val onTextUpdated: (position: Int, text: String) -> Unit,
    val onEditDismissed: () -> Unit,
) {

    companion object {
        val PREVIEW_STATE = EditDialogState(
            flowEditDialogType = MutableStateFlow(EditDialogType.Close),
            onTextUpdated = { _, _ -> },
            onEditDismissed = {}
        )
    }
}

sealed interface EditDialogType {
    data class Open(
        val position: Int,
        val itemPurchaseCategoryModel: PurchaseCategoryModelUI
    ) : EditDialogType

    data object Close : EditDialogType
}

