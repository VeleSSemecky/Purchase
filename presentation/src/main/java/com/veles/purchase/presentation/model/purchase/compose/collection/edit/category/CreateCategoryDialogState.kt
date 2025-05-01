package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateCategoryDialogState(
    val flowCreateCategoryDialogType: StateFlow<CreateCategoryDialogType>,
    val onCreateCategoryClicked: (text: String) -> Unit,
    val onCreateCategoryDismiss: () -> Unit,
) {

    companion object {
        val PREVIEW_STATE = CreateCategoryDialogState(
            flowCreateCategoryDialogType = MutableStateFlow(CreateCategoryDialogType.Open),
            onCreateCategoryClicked = { _ -> },
            onCreateCategoryDismiss = {}
        )
    }
}

sealed interface CreateCategoryDialogType {
    data object Open : CreateCategoryDialogType
    data object Close : CreateCategoryDialogType
}

