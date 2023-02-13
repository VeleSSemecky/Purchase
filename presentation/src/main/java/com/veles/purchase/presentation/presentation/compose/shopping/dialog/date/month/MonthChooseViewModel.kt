package com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.data.room.util.LocalDateTimeConverter.getFullLocalMonthName
import com.veles.purchase.domain.utill.zeroInt
import com.veles.purchase.domain.utill.zeroString
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.MonthEvent
import java.time.LocalDateTime
import java.time.Month
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MonthChooseViewModel @Inject constructor(
    private val sharedFlowBus: SharedFlowBus,
    args: MonthChooseFragmentArgs
) : ViewModel() {

    val flowLocalDataMonth: MutableStateFlow<Int> =
        MutableStateFlow(
            if (args.month == zeroString()) LocalDateTime.now().year else args.month.toInt()
        )

    fun setLocalDataMonth(month: Int) = viewModelScope.launch {
        flowLocalDataMonth.emit(month)
    }

    fun getMonthArrayNames() =
        Month.values().map { month -> month.getFullLocalMonthName() }.toTypedArray()

    fun getMonthArrayValue() = Month.values().map { month -> month.value }.toTypedArray()

    fun save() = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(MonthEvent(flowLocalDataMonth.value))
    }

    fun all() = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(MonthEvent(zeroInt()))
    }
}
