package com.veles.purchase.domain.model

import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.domain.utill.emptyString

/**
 * A single line item (product) that belongs to a purchase ([SkuModel]).
 *
 * One purchase (the receipt total) can contain many [SkuItemModel]s, each
 * describing an individual product recognised from the receipt.
 *
 * @param skuItemId stable primary key
 * @param skuId foreign key to the parent [SkuModel.skuId]
 * @param name product name exactly as printed
 * @param quantity printed quantity, kept as a free-form string ("1", "2", "0.5 kg")
 * @param price line total for this product (unit price × quantity)
 * @param taxRate taxation info if available ("23%", "A", "") — empty when unknown
 */
data class SkuItemModel(
    val skuItemId: String = createPrimaryIDKey(),
    val skuId: String = emptyString(),
    val name: String = emptyString(),
    val quantity: String = emptyString(),
    val price: String = emptyString(),
    val taxRate: String = emptyString()
)

