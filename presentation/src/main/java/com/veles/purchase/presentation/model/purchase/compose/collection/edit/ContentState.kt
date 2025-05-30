package com.veles.purchase.presentation.model.purchase.compose.collection.edit

import com.veles.purchase.presentation.model.user.UserCheckedUI
import com.veles.purchase.presentation.model.user.UserPurchaseModelUI

data class ContentState(
    val listUserChecked: List<UserCheckedUI>,
    val componentNameState: ComponentNameState,
    val categoryState: CategoryState,
    val itemUserState: ItemUserState,
    val toolBarState: ToolBarState,
) {

    companion object {
        val PREVIEW_STATE = ContentState(
            listUserChecked = listOf(
                UserCheckedUI(
                    userPurchase = UserPurchaseModelUI(
                        uid = "user1",
                        displayName = "Alice Wonderland",
                        email = ""
                    )
                )
            ),
            componentNameState = ComponentNameState.PREVIEW_STATE,
            categoryState = CategoryState.PREVIEW_STATE,
            itemUserState = ItemUserState.PREVIEW_STATE,
            toolBarState = ToolBarState.PREVIEW_STATE
        )
    }
}
