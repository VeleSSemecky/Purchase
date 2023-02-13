package com.veles.purchase.presentation.presentation.compose.shopping.edit

import android.icu.util.Currency
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.usecase.sku.GetSkuPhotoUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import com.veles.purchase.domain.usecase.sku.SetSkuUseCase
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.presentation.data.bus.SharedFlowBus
import com.veles.purchase.presentation.extensions.toLocalDateTime
import com.veles.purchase.presentation.model.core.TextFieldModel
import com.veles.purchase.presentation.model.core.anyError
import com.veles.purchase.presentation.model.core.createTextFieldModel
import com.veles.purchase.presentation.model.event.CurrencyEvent
import com.veles.purchase.presentation.model.event.DialogEvent
import com.veles.purchase.presentation.model.event.SkuPhotoDeleteEvent
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SkuEditViewModel @Inject constructor(
    private val setSkuUseCase: SetSkuUseCase,
    private val getSkuUseCase: GetSkuUseCase,
    private val getSkuPhotoUseCase: GetSkuPhotoUseCase,
    private val skuEditFragmentArgs: SkuEditFragmentArgs,
    private val sharedFlowBus: SharedFlowBus
) : ViewModel() {

    val flowSkuLocalData: MutableStateFlow<TextFieldModel<LocalDateTime>> =
        MutableStateFlow(LocalDateTime.now().createTextFieldModel())
    val flowSkuName: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow(emptyString().createTextFieldModel())
    val flowSkuComment: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow(emptyString().createTextFieldModel())
    val flowSkuPrice: MutableStateFlow<TextFieldModel<String>> =
        MutableStateFlow(emptyString().createTextFieldModel())
    val flowSkuCurrency: MutableStateFlow<Currency> =
        MutableStateFlow(Currency.getInstance(Locale.getDefault()))
    val flowSkuPhotoEntityList: MutableStateFlow<List<SkuPhotoModel>> =
        MutableStateFlow(emptyList())

    init {
        onUpdateData()
        onCurrencyEvent()
        onSkuPhotoDeleteEvent()
    }

    private fun onCurrencyEvent() = sharedFlowBus.getSharedFlow(CurrencyEvent::class).onEach {
        flowSkuCurrency.value = Currency.getInstance(it.currencyCode)
    }.launchIn(viewModelScope)

    private fun onSkuPhotoDeleteEvent() =
        sharedFlowBus.getSharedFlow(SkuPhotoDeleteEvent::class)
            .onEach { skuPhotoDeleteEvent: SkuPhotoDeleteEvent ->
                flowSkuPhotoEntityList.value = flowSkuPhotoEntityList.value.toMutableList().apply {
                    remove(find { it.skuPhotoId == skuPhotoDeleteEvent.skuPhotoEntity.skuPhotoId })
                }
            }.launchIn(viewModelScope)

    fun save(onSuccess: () -> Unit) = viewModelScope.launch {
        flowSkuName.createTextFieldModel { isEmpty() }
        flowSkuPrice.createTextFieldModel { isEmpty() }
        if (anyError(flowSkuName, flowSkuPrice)) return@launch
        val skuEntity = SkuModel(
            skuId = skuEditFragmentArgs.skuId,
            skuLocalData = flowSkuLocalData.value.model,
            skuName = flowSkuName.value.model,
            skuComment = flowSkuComment.value.model,
            skuPrice = flowSkuPrice.value.model,
            skuCurrencyCode = flowSkuCurrency.value.currencyCode
        )
        setSkuUseCase(skuEntity = skuEntity, skuPhotoEntityList = flowSkuPhotoEntityList.value)
        sharedFlowBus.setSharedFlow(DialogEvent())
        onSuccess()
    }

    fun setSkuLocalData(data: Long) = viewModelScope.launch {
        flowSkuLocalData.emit(data.toLocalDateTime().createTextFieldModel())
    }

    fun setSkuName(name: String) = viewModelScope.launch {
        flowSkuName.emit(name.createTextFieldModel(name.isEmpty()))
    }

    fun setSkuComment(comment: String) = viewModelScope.launch {
        flowSkuComment.emit(comment.createTextFieldModel())
    }

    fun setSkuPrice(price: String) = viewModelScope.launch {
        flowSkuPrice.emit(price.createTextFieldModel(price.isEmpty()))
    }

    fun setSkuPhotoEntity(photoUri: Uri) = viewModelScope.launch {
        flowSkuPhotoEntityList.emit(
            flowSkuPhotoEntityList.value.toMutableList().apply {
                add(
                    SkuPhotoModel(
                        skuId = skuEditFragmentArgs.skuId,
                        skuPhotoUri = photoUri.toString(),
                        skuPhotoId = createPrimaryIDKey()
                    )
                )
            }
        )
    }

    private fun onUpdateData() = viewModelScope.launch {
        val id = skuEditFragmentArgs.skuId
        getSkuUseCase.getSkuModel(id)?.apply {
            flowSkuLocalData.value = skuLocalData.createTextFieldModel()
            flowSkuName.value = skuName.createTextFieldModel()
            flowSkuComment.value = skuComment.createTextFieldModel()
            flowSkuPrice.value = skuPrice.createTextFieldModel()
            flowSkuCurrency.value = Currency.getInstance(skuCurrencyCode)
        }
        flowSkuPhotoEntityList.emit(getSkuPhotoUseCase(id))
    }
}
