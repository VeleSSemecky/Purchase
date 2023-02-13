package com.veles.purchase.presentation.model.event

import com.veles.purchase.domain.utill.zeroInt
import com.veles.purchase.presentation.data.bus.Event
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonthEvent(
    val monthOrAll: Int = zeroInt()
) : Event
