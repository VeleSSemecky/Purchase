package com.veles.purchase.domain.model.scanner

data class ReceiptItem(
    val name: String,
    val price: Double,
    val currency: String = ""
)

data class ReceiptData(
    val totalAmount: Double?,
    val currency: String,
    val items: List<ReceiptItem>
)
