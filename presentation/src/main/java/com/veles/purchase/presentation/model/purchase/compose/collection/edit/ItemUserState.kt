package com.veles.purchase.presentation.model.purchase.compose.collection.edit

import com.veles.purchase.presentation.model.user.UserCheckedUI

data class ItemUserState(
    val onUpdateCheck: (id: Int, item: UserCheckedUI) -> Unit,
) {

    companion object {
        val PREVIEW_STATE = ItemUserState(
            onUpdateCheck = { _, _ -> }
        )
    }
}
