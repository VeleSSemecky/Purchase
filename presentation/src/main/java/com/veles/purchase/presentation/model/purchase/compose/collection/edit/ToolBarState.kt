package com.veles.purchase.presentation.model.purchase.compose.collection.edit

data class ToolBarState(
    val save: () -> Unit
){

    companion object {
        val PREVIEW_STATE = ToolBarState(
            save = { }
        )
    }
}
