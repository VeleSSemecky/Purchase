package com.veles.purchase.presentation.model.purchase.compose.collection.edit

import com.veles.purchase.presentation.model.purchase.compose.core.ProgressState
import com.veles.purchase.presentation.model.user.UserCheckedUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ContentState(
    val flowListUserChecked: StateFlow<List<UserCheckedUI>>,
    val componentNameState: ComponentNameState,
    val categoryState: CategoryState,
    val itemUserState: ItemUserState,
    val toolBarState: ToolBarState,
    val progressState: ProgressState,
) {

    companion object {
        val PREVIEW_STATE = ContentState(
            flowListUserChecked = MutableStateFlow(emptyList()),
            componentNameState = ComponentNameState.PREVIEW_STATE,
            categoryState = CategoryState.PREVIEW_STATE,
            itemUserState = ItemUserState.PREVIEW_STATE,
            toolBarState = ToolBarState.PREVIEW_STATE,
            progressState = ProgressState.PREVIEW_STATE
        )
    }
}
