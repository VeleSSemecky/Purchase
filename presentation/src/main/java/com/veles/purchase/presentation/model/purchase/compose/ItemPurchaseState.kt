package com.veles.purchase.presentation.model.purchase.compose

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.setting.PurchaseSetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ItemPurchaseState(
    val elevation: Dp = 4.dp,
    val item: PurchaseModel = PurchaseModel.TEST,
    val flowPurchaseSetting: StateFlow<PurchaseSetting> = MutableStateFlow(PurchaseSetting()),
    val onItemClicked: (item: PurchaseModel) -> Unit = { },
    val onLongClicked: (item: PurchaseModel) -> Unit = { },
    val onChecked: (item: PurchaseModel) -> Unit = { },
)
