package com.example.shared.domain.model.purchase

import com.veles.purchase.domain.util.TimeProvider

data class PurchaseModel(
    val id: String,
    val text: String,
    val count: String,
    val isChecked: Boolean,
    val time: Long,
    val price: String,
    val collectionId: String
)

/**
 * Extension function to generate unique ID for new purchases
 */
fun generateId(): String {
    return "${TimeProvider.currentTimeMillis()}_${(0..9999).random()}"
}
