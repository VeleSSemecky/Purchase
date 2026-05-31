package com.veles.purchase.domain.model.scanner

data class ScannedProduct(
    val name: String,
    val price: String,
    val currency: String
) {
    val isEmpty: Boolean get() = name.isEmpty() && price.isEmpty()
}
