package com.veles.purchase.presentation.model.event

import com.veles.purchase.presentation.data.bus.Event
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyEvent(
    val currencyCode: String
) : Event
