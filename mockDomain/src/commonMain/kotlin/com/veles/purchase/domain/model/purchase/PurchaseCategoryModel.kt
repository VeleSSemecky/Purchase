package com.veles.purchase.domain.model.purchase

data class PurchaseCategoryModel(
    val id: String,
    val name: String
) {
    companion object {
        val EMPTY = PurchaseCategoryModel(
            id = "",
            name = ""
        )
        val TEST = PurchaseCategoryModel(
            id = "test_id",
            name = "Test Category"
        )
    }
}

