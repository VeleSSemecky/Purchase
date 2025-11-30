package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.model.user.UserPurchaseModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Purchase collection model
 *
 * Original: /domain/src/main/java/com/veles/purchase/domain/model/purchase/PurchaseCollectionModel.kt
 * Adapted for KMP
 *
 * Represents a collection (group) of purchases, like "Groceries", "Monthly Shopping", etc.
 */
data class PurchaseCollectionModel(
    val id: String,
    val name: String,
    val creator: UserPurchaseModel,
    val listMembers: List<String>,
    val categoryModels: List<PurchaseCategoryModel>
) {

    companion object {

        val EMPTY = PurchaseCollectionModel(
            id = "",
            name = "",
            creator = UserPurchaseModel.EMPTY,
            listMembers = emptyList(),
            categoryModels = emptyList()
        )

        @OptIn(ExperimentalUuidApi::class)
        val TEST = PurchaseCollectionModel(
            id = Uuid.random().toString(),
            name = "Test Collection",
            creator = UserPurchaseModel.MOCK_USER,
            listMembers = listOf("mock_user_123", "mock_user_456"),
            categoryModels = listOf(
                PurchaseCategoryModel(
                    id = Uuid.random().toString(),
                    name = "Groceries"
                ),
                PurchaseCategoryModel(
                    id = Uuid.random().toString(),
                    name = "Household"
                )
            )
        )
    }
}

/**
 * Check if collection has more than one member
 */
fun PurchaseCollectionModel.isMoreThanOneMembers() = listMembers.size - 1 > 0

/**
 * Count additional members (excluding creator)
 */
fun PurchaseCollectionModel.countAdditionalMembers() = listMembers.size - 1