package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category

import com.veles.purchase.presentation.model.purchase.PurchaseCategoryModelUI

data class CategoryScreenState(
    val isLoading: Boolean = false,
    val categories: List<PurchaseCategoryModelUI> = emptyList(),
    val dialogState: DialogState = DialogState.NoDialog
) {
    companion object {
        val PREVIEW_STATE = CategoryScreenState(
            categories = listOf(
                PurchaseCategoryModelUI(id = "1", name = "Category 1"),
                PurchaseCategoryModelUI(id = "2", name = "Category 2")
            )
        )
    }
}

sealed class DialogState {
    data object NoDialog : DialogState()
    data class EditCategoryDialog(val position: Int, val item: PurchaseCategoryModelUI) : DialogState()
    data object CreateCategoryDialog : DialogState()
    data object ConfirmLeaveDialog : DialogState()
}

