package com.veles.purchase.domain.model.scanner

data class ReceiptItem(
    val name: String,
    val price: Double,
    val currency: String = "",
    /** Printed quantity, kept free-form ("1", "2", "0.5 kg"). Empty when unknown. */
    val quantity: String = "",
    /** Taxation info if printed ("23%", "A", ...). Empty when unavailable. */
    val taxRate: String = ""
)

data class ReceiptData(
    val totalAmount: Double?,
    val currency: String,
    val items: List<ReceiptItem>
)
