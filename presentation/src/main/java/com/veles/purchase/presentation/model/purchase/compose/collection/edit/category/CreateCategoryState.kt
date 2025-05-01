package com.veles.purchase.presentation.model.purchase.compose.collection.edit.category

import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI

data class CreateCategoryState(
    val onItemClicked: (position: Int, item: PurchaseCategoryModelUI) -> Unit,
    val onRemoveCategory: (item: PurchaseCategoryModelUI) -> Unit,
) {

    companion object {
        val PREVIEW_STATE = CreateCategoryState(
            onItemClicked = { _, _ -> },
            onRemoveCategory = { _ -> }
        )
    }
}
