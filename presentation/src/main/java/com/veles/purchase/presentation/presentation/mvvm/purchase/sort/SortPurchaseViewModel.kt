package com.veles.purchase.presentation.presentation.mvvm.purchase.sort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.SortPurchaseEvent
import com.veles.purchase.presentation.model.sort.SortPurchase
import javax.inject.Inject
import kotlinx.coroutines.launch

class SortPurchaseViewModel @Inject constructor(
    private val sharedFlowBus: SharedFlowBus,
    private val router: Router
) : ViewModel() {

    fun sendSort(sortPurchase: SortPurchase) = viewModelScope.launch {
        sharedFlowBus.setSharedFlow(SortPurchaseEvent(sortPurchase))
        router().popBackStack()
    }
}
