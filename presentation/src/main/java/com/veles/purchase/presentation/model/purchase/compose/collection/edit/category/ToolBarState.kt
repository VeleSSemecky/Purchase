package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

data class ToolBarState(
    val onBackClicked: () -> Unit,
    val onSaveClicked: () -> Unit,
){

    companion object {
        val PREVIEW_STATE = ToolBarState(
            onBackClicked = { },
            onSaveClicked = { }
        )
    }
}
