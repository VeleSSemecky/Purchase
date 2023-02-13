package com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.extensions.toCurrency
import com.veles.purchase.presentation.model.currency.CurrencyModel
import com.veles.purchase.presentation.model.currency.currencyModelList
import com.veles.purchase.presentation.model.event.CurrencyEvent
import java.util.Currency
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CurrencySearchViewModel @Inject constructor(
    private val sharedFlowBus: SharedFlowBus,
    args: CurrencySearchFragmentArgs
) : ViewModel() {

    private val flowCurrency: MutableStateFlow<Currency> =
        MutableStateFlow(args.currencyCode.toCurrency())

    val flowCurrencyModelList: MutableStateFlow<List<CurrencyModel>> =
        MutableStateFlow(currencyModelList)

    val flowValueSearch: MutableStateFlow<String> = MutableStateFlow(emptyString())

    fun setCurrency(currencyCode: String) = viewModelScope.launch {
        flowCurrency.emit(currencyCode.toCurrency())
    }

    fun setCurrencyModelList(currencyModelList: List<CurrencyModel>) = viewModelScope.launch {
        flowCurrencyModelList.emit(currencyModelList)
    }

    fun setValueSearch(valueSearch: String) = viewModelScope.launch {
        flowValueSearch.emit(valueSearch)
    }

    fun save() = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(CurrencyEvent(flowCurrency.value.currencyCode))
    }
}
