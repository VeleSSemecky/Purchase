package com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.extensions.toCurrency
import com.veles.purchase.presentation.model.event.CurrencyEvent
import java.util.Currency
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CurrencyChooseViewModel @Inject constructor(
    private val sharedFlowBus: SharedFlowBus,
    args: CurrencyChooseFragmentArgs
) : ViewModel() {

    val flowCurrency: MutableStateFlow<Currency> = MutableStateFlow(args.currencyCode.toCurrency())

    fun setCurrency(currencyCode: String) = viewModelScope.launch {
        flowCurrency.emit(Currency.getInstance(currencyCode))
    }

    fun save() = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(CurrencyEvent(flowCurrency.value.currencyCode))
    }
}
