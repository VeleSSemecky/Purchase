package com.veles.purchase.presentation.presentation.mvvm.purchase.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.usecase.storage.GetPhotoStorageUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.model.event.PurchasePhotoDeleteEvent
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.toPurchasePhotoModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoPurchaseComposeViewModel @Inject constructor(
    private val args: PhotoPurchaseComposeFragmentArgs,
    private val router: Router,
    private val sharedFlowBus: SharedFlowBus,
    private val getPhotoStorageUseCase: GetPhotoStorageUseCase
) : ViewModel() {

    val flowPurchasePhotoModel: MutableStateFlow<PurchasePhotoModel?> =
        MutableStateFlow(args.modelPurchasePhoto?.toPurchasePhotoModel())

    val flowProgress: MutableStateFlow<Progress> =
        MutableStateFlow(Progress.End)

    fun deletePhoto() = viewModelScope.launch {
        flowProgress.emit(Progress.Start)
        val modelPurchasePhoto = args.modelPurchasePhoto ?: return@launch
        sharedFlowBus.setSharedFlow(PurchasePhotoDeleteEvent(modelPurchasePhoto))
        flowProgress.emit(Progress.End)
        router().popBackStack()
    }

    fun apiDatabaseURL(purchasePhotoModel: PurchasePhotoModel): Any =
        getPhotoStorageUseCase(purchasePhotoModel)

    fun onBackClicked() = router().popBackStack()
}
