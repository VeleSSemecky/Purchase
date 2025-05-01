package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

data class FabState(
    val onCreateCategoryDialogClicked: () -> Unit
) {

    companion object {
        val PREVIEW_STATE = FabState(
            onCreateCategoryDialogClicked = { }
        )
    }
}
