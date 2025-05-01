package com.veles.purchase.presentation.model.purchase.compose.core

import com.veles.purchase.presentation.model.progress.Progress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProgressState(
    val flowProgress: StateFlow<Progress>
) {

    companion object {
        val PREVIEW_STATE = ProgressState(
            flowProgress = MutableStateFlow(Progress.End)
        )
    }
}
