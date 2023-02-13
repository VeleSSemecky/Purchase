package com.veles.purchase.presentation.model.event

import com.veles.purchase.presentation.data.bus.Event
import com.veles.purchase.presentation.model.sort.SortPurchase
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortPurchaseEvent(
    val sortPurchase: SortPurchase
) : Event
