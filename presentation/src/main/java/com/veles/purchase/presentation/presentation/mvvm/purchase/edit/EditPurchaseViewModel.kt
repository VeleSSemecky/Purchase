package com.veles.purchase.presentation.presentation.mvvm.purchase.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.usecase.price.PriceUseCase
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import com.veles.purchase.domain.usecase.storage.GetPhotoStorageUseCase
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.domain.utill.zeroString
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.extensions.toLocalDateTime
import com.veles.purchase.presentation.model.core.PhotoStateModel
import com.veles.purchase.presentation.model.core.TextFieldModel
import com.veles.purchase.presentation.model.core.anyError
import com.veles.purchase.presentation.model.core.changePhotoStateModel
import com.veles.purchase.presentation.model.core.createListPhotoStateModels
import com.veles.purchase.presentation.model.core.createTextFieldModel
import com.veles.purchase.presentation.model.core.emitPhotoStateModel
import com.veles.purchase.presentation.model.core.getModel
import com.veles.purchase.presentation.model.event.PurchasePhotoDeleteEvent
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.model.purchase.PurchaseModelUI
import com.veles.purchase.presentation.model.purchase.toPurchaseCollectionModel
import com.veles.purchase.presentation.model.purchase.toPurchaseModel
import com.veles.purchase.presentation.model.purchase.toPurchasePhotoModel
import java.time.LocalDateTime
import java.util.Currency
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EditPurchaseViewModel @Inject constructor(
    private val args: EditPurchaseFragmentArgs,
    private val sharedFlowBus: SharedFlowBus,
    private val priceUseCase: PriceUseCase,
    private val getPhotoStorageUseCase: GetPhotoStorageUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase,
    private val navController: NavController
) : ViewModel() {

    val flowPurchaseLocalData: MutableStateFlow<TextFieldModel<LocalDateTime>> =
        MutableStateFlow(LocalDateTime.now().createTextFieldModel())
    val flowPurchaseName: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow((args.purchaseModel?.text ?: emptyString()).createTextFieldModel())
    val flowPurchaseComment: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow((args.purchaseModel?.count ?: emptyString()).createTextFieldModel())
    val flowPurchasePrice: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow((args.purchaseModel?.price ?: zeroString()).createTextFieldModel())
    val flowPurchaseCurrency: MutableStateFlow<Currency> =
        MutableStateFlow(Currency.getInstance(Locale.getDefault()))
    val flowPurchasePhotoModelList: MutableStateFlow<List<PhotoStateModel<PurchasePhotoModel>>> =
        MutableStateFlow(
            args.purchaseModel?.toPurchaseModel()?.listImage.createListPhotoStateModels()
        )

    val flowProgress: MutableStateFlow<Progress> =
        MutableStateFlow(Progress.End)

    init {
        onPhotoDeleteEvent()
    }

    fun setSkuLocalData(data: Long) = viewModelScope.launch {
        flowPurchaseLocalData.emit(data.toLocalDateTime().createTextFieldModel())
    }

    fun setPurchaseName(name: String) = viewModelScope.launch {
        flowPurchaseName.emit(name.createTextFieldModel(name.isEmpty()))
    }

    fun setPurchasePrice(price: String) = viewModelScope.launch {
        flowPurchasePrice.emit(price.createTextFieldModel(price.isEmpty()))
    }

    fun setPurchaseComment(comment: String) = viewModelScope.launch {
        flowPurchaseComment.emit(comment.createTextFieldModel())
    }

    fun setPurchasePhotoModel(photoUri: Uri) = viewModelScope.launch {
        flowPurchasePhotoModelList.emitPhotoStateModel(
            PurchasePhotoModel(
                purchaseId = args.purchaseModel?.createId ?: emptyString(),
                purchasePhotoUri = photoUri.toString()
            )
        )
    }

    fun save() = viewModelScope.launch {
        flowPurchaseName.createTextFieldModel { isEmpty() }
        flowPurchaseComment.createTextFieldModel { isEmpty() }
        flowPurchasePrice.createTextFieldModel(priceUseCase()) { isEmpty() }
        if (anyError(flowPurchaseName, flowPurchasePrice)) return@launch

        flowProgress.emit(Progress.Start)
        val model = (args.purchaseModel ?: PurchaseModelUI()).toPurchaseModel().copy(
            text = flowPurchaseName.value.model,
            count = flowPurchaseComment.value.model,
            price = flowPurchasePrice.value.model,
            listImage = flowPurchasePhotoModelList.getModel { it.isNeedDelete.not() }
        )

        savePurchaseUseCase(
            modelCollectionPurchase = args.modelCollectionPurchase?.toPurchaseCollectionModel(),
            purchaseModel = model,
            isNewPurchase = args.purchaseModel == null,
            listPurchasePhotoModel = flowPurchasePhotoModelList.getModel { it.isNeedDelete }
        )

        flowProgress.emit(Progress.End)
        navController.popBackStack()
    }

    fun apiDatabaseURL(purchaseModel: PurchasePhotoModel): Any =
        getPhotoStorageUseCase(purchaseModel)

    private fun onPhotoDeleteEvent() = sharedFlowBus.getSharedFlow(PurchasePhotoDeleteEvent::class)
        .onEach { purchasePhotoDeleteEvent ->
            flowPurchasePhotoModelList.changePhotoStateModel(
                model = purchasePhotoDeleteEvent.purchasePhotoModel.toPurchasePhotoModel(),
                isNeedDelete = true
            )
        }.launchIn(viewModelScope)
}
