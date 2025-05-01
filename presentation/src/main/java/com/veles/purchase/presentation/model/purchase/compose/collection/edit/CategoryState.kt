package com.veles.purchase.presentation.model.purchase.compose.collection.edit

class CategoryState(
    val onCategoryClicked: () -> Unit,
) {

    companion object {
        val PREVIEW_STATE = CategoryState(
            onCategoryClicked = { }
        )
    }
}
