package com.veles.purchase.presentation.presentation.compose.shopping.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.usecase.sku.DeleteSkuUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.DialogEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SkuListViewModel @Inject constructor(
    private val deleteSkuUseCase: DeleteSkuUseCase,
    private val getSkuUseCase: GetSkuUseCase,
    private val sharedFlowBus: SharedFlowBus
) : ViewModel() {

    val flowSkuEntityList: MutableStateFlow<List<SkuModel>> = MutableStateFlow(emptyList())

    init {
        initList()
        updateList()
    }

    fun initList() = viewModelScope.launch {
        val list = getSkuUseCase.getSkuModelList()
        flowSkuEntityList.emit(list)
    }

    private fun updateList() = sharedFlowBus.getSharedFlow(DialogEvent::class).onEach {
        initList()
    }.launchIn(viewModelScope)

    fun deleteSkuEntity(id: String) = viewModelScope.launch {
        deleteSkuUseCase(id)
        initList()
    }
}
