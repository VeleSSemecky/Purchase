package com.veles.purchase.presentation.presentation.compose.shopping.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.usecase.sku.GetSkuSumMontUseCase
import com.veles.purchase.domain.utill.zeroInt
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.MonthEvent
import com.veles.purchase.presentation.model.event.YearEvent
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OutlayGraphViewModel @Inject constructor(
    private val getSkuSumMontUseCase: GetSkuSumMontUseCase,
    private val sharedFlowBus: SharedFlowBus
) : ViewModel() {

    val flowSkuSumMonthModelList: MutableStateFlow<List<SkuSumMonthModel>> =
        MutableStateFlow(emptyList())
    val flowSkuYear: MutableStateFlow<Int> = MutableStateFlow(LocalDateTime.now().year)
    val flowSkuMonth: MutableStateFlow<Int> = MutableStateFlow(zeroInt())

    init {
        updateYear()
        onYearEvent()
        onMonthEvent()
    }

    private fun onYearEvent() = sharedFlowBus.getSharedFlow(YearEvent::class).onEach {
        flowSkuYear.emit(it.year)
        updateYear()
    }.launchIn(viewModelScope)

    private fun onMonthEvent() = sharedFlowBus.getSharedFlow(MonthEvent::class).onEach {
        flowSkuMonth.emit(it.monthOrAll)
        updateYear()
    }.launchIn(viewModelScope)

    private fun updateYear() = viewModelScope.launch {
        flowSkuSumMonthModelList.emit(getSkuSumMontUseCase(flowSkuYear.value, flowSkuMonth.value))
    }
}
