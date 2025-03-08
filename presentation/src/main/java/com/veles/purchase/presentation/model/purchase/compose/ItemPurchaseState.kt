package com.veles.purchase.presentation.model.purchase.compose

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ItemPurchaseState(
    val flowPurchaseSetting: StateFlow<PurchaseSetting>,
    val onItemClicked: (item: PurchaseModel) -> Unit,
    val onLongClicked: (item: PurchaseModel) -> Unit,
    val onChecked: (item: PurchaseModel) -> Unit = { }
) {

    companion object {
        val PREVIEW_STATE = ItemPurchaseState(
            flowPurchaseSetting = MutableStateFlow(PurchaseSetting()),
            onItemClicked = { },
            onLongClicked = { },
            onChecked = { }
        )
    }
}
