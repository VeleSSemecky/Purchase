package com.veles.purchase.presentation.model.purchase.compose.history

data class HistoryToolBarState(
    val onBackClicked: () -> Unit
) {

    companion object {
        val PREVIEW_STATE = HistoryToolBarState(
            onBackClicked = { }
        )
    }
}
