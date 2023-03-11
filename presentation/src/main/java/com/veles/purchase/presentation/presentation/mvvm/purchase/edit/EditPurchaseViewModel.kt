package com.veles.purchase.presentation.presentation.mvvm.purchase.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.core.extensions.add
import com.veles.purchase.domain.core.extensions.change
import com.veles.purchase.domain.core.extensions.emitNotNull
import com.veles.purchase.domain.core.extensions.invoke
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.usecase.purchase.GetPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import com.veles.purchase.domain.usecase.storage.GetPhotoStorageUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.extensions.LocaleCurrency
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.extensions.mapStateIn
import com.veles.purchase.presentation.extensions.toLocalDateTime
import com.veles.purchase.presentation.model.event.PurchasePhotoDeleteEvent
import com.veles.purchase.presentation.model.progress.Progress
import java.time.LocalDateTime
import java.util.Currency
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EditPurchaseViewModel @Inject constructor(
    private val args: EditPurchaseFragmentArgs,
    private val sharedFlowBus: SharedFlowBus,
    private val getPurchaseUseCase: GetPurchaseUseCase,
    private val getPhotoStorageUseCase: GetPhotoStorageUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase,
    private val router: Router
) : ViewModel() {

    private val flowPurchaseModel: MutableStateFlow<PurchaseModel> =
        MutableStateFlow(PurchaseModel.EMPTY)

    private val flowDeletePurchasePhotoModelList: MutableStateFlow<List<PurchasePhotoModel>> =
        MutableStateFlow(emptyList())

    val flowPurchaseLocalData: MutableStateFlow<LocalDateTime> =
        MutableStateFlow(LocalDateTime.now())

    val flowPurchaseName: StateFlow<String>
        get() = flowPurchaseModel.mapStateIn(viewModelScope) { it.text }

    val flowPurchaseComment: StateFlow<String>
        get() = flowPurchaseModel.mapStateIn(viewModelScope) { it.count }

    val flowPurchasePrice: StateFlow<String>
        get() = flowPurchaseModel.mapStateIn(viewModelScope) { it.price }

    val flowPurchaseCurrency: MutableStateFlow<Currency> = MutableStateFlow(LocaleCurrency())

    val flowPurchasePhotoModelList: StateFlow<List<PurchasePhotoModel>>
        get() = flowPurchaseModel.mapStateIn(viewModelScope) { it.listImage }

    val flowPurchaseIsChecked: StateFlow<Boolean>
        get() = flowPurchaseModel.mapStateIn(viewModelScope) { it.check }

    val flowProgress: MutableStateFlow<Progress> = MutableStateFlow(Progress.End)

    init {
        getPurchaseModel()
        onPhotoDeleteEvent()
    }

    fun onCheckedChange(isChecked: Boolean) = flowPurchaseModel.change(viewModelScope) { value ->
        value.copy(check = isChecked)
    }

    fun setPurchaseLocalData(data: Long) = viewModelScope.launch {
        flowPurchaseLocalData.emit(data.toLocalDateTime())
    }

    fun onTitleChange(name: String) = flowPurchaseModel.change(viewModelScope) { value ->
        value.copy(text = name)
    }

    fun onPriceChange(price: String) = flowPurchaseModel.change(viewModelScope) { value ->
        value.copy(price = price)
    }

    fun onCommentChange(comment: String) = flowPurchaseModel.change(viewModelScope) { value ->
        value.copy(count = comment)
    }

    fun setPurchasePhotoModel(photoUri: Uri) = flowPurchaseModel.change(viewModelScope) { value ->
        val purchasePhotoModel = PurchasePhotoModel(
            purchaseId = flowPurchaseModel().createId,
            purchasePhotoUri = photoUri.toString()
        )
        val listImage = value.listImage.toMutableList()
        listImage.add(purchasePhotoModel)
        value.copy(listImage = listImage)
    }

    fun onSaveClicked() = viewModelScope.launch {
        if (flowPurchaseName().isBlank()) return@launch

        flowProgress.emit(Progress.Start)

        savePurchaseUseCase(
            purchaseCollectionId = args.purchaseCollectionId,
            editPurchaseModel = flowPurchaseModel(),
            isNewPurchase = args.purchaseId.isEmpty(),
            listPurchasePhotoModel = flowDeletePurchasePhotoModelList()
        )

        flowProgress.emit(Progress.End)
        router().popBackStack()
    }

    fun apiDatabaseURL(purchaseModel: PurchasePhotoModel): Any =
        getPhotoStorageUseCase(purchaseModel)

    private fun getPurchaseModel() = viewModelScope.launchOnError {
        flowProgress.emit(Progress.Start)
        val purchaseModel = getPurchaseUseCase(
            args.purchaseCollectionId,
            args.purchaseId
        )
        flowPurchaseModel.emitNotNull(purchaseModel)
        flowProgress.emit(Progress.End)
    }

    private fun onPhotoDeleteEvent() = sharedFlowBus.getSharedFlow(PurchasePhotoDeleteEvent::class)
        .onEach { purchasePhotoDeleteEvent ->
            val deletePhoto = flowPurchaseModel().listImage.find {
                it.purchasePhotoId == purchasePhotoDeleteEvent.purchasePhotoModel.purchasePhotoId
            } ?: return@onEach
            flowPurchaseModel.change { value ->
                value.copy(listImage = value.dropImage(deletePhoto))
            }
            flowDeletePurchasePhotoModelList.change { value ->
                value.add(deletePhoto)
            }
        }.launchIn(viewModelScope)
}
