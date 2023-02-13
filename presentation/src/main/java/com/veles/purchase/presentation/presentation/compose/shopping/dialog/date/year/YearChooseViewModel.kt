package com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.year

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.utill.zeroInt
import com.veles.purchase.domain.utill.zeroString
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.YearEvent
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class YearChooseViewModel @Inject constructor(
    private val sharedFlowBus: SharedFlowBus,
    args: YearChooseFragmentArgs
) : ViewModel() {

    val flowLocalDataYear: MutableStateFlow<Int> =
        MutableStateFlow(
            if (args.year == zeroString()) LocalDateTime.now().year else args.year.toInt()
        )

    fun setLocalDataYear(year: Int) = viewModelScope.launch {
        flowLocalDataYear.emit(year)
    }

    fun save() = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(YearEvent(flowLocalDataYear.value))
    }

    fun all() = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(YearEvent(zeroInt()))
    }
}
