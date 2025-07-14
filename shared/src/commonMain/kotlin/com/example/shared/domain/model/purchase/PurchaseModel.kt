package com.example.shared.domain.model.purchase

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
@OptIn(ExperimentalTime::class)
fun generateId(): String {
    return "${Clock.System.now().epochSeconds}_${(0..9999).random()}"
}
