package com.veles.purchase.presentation.presentation.compose.shopping.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.data.room.table.SkuPhotoEntity
import com.veles.purchase.domain.usecase.sku.DeleteSkuPhotoUseCase
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.SkuPhotoDeleteEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel @Inject constructor(
    private val deleteSkuPhotoUseCase: DeleteSkuPhotoUseCase,
    private val args: PhotoListFragmentArgs,
    private val sharedFlowBus: SharedFlowBus
) : ViewModel() {

    val flowSkuPhotoEntity: MutableStateFlow<SkuPhotoEntity?> =
        MutableStateFlow(args.skuPhotoEntity)

    fun deletePhoto(onBack: () -> Unit) = viewModelScope.launch {
        args.skuPhotoEntity?.let {
            deleteSkuPhotoUseCase(it.skuPhotoId)
            sharedFlowBus.setSharedFlow(SkuPhotoDeleteEvent(it))
        }
        onBack()
    }
}
