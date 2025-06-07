package com.veles.purchase.presentation.model.purchase.compose.collection.edit

class HistoryState(
    val onHistoryClicked: () -> Unit,
) {

    companion object {
        val PREVIEW_STATE = HistoryState(
            onHistoryClicked = { }
        )
    }
}
