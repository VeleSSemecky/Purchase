package com.veles.purchase.presentation.presentation.mvvm.purchase.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import com.veles.purchase.domain.usecase.setting.GetSettingUseCase
import com.veles.purchase.domain.usecase.setting.SetSettingUseCase
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.model.setting.CornerSetting
import com.veles.purchase.presentation.model.setting.setCorner
import com.veles.purchase.presentation.model.setting.toCornerSetting
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingPurchaseComposeViewModel @Inject constructor(
    private val getSettingUseCase: GetSettingUseCase,
    private val setSettingUseCase: SetSettingUseCase,
    private val router: Router
) : ViewModel() {

    private val _flowPurchaseSetting = MutableStateFlow(PurchaseSetting())
    val flowPurchaseSetting: StateFlow<PurchaseSetting>
        get() = _flowPurchaseSetting.asStateFlow()

    private val _flowAllCorner = MutableStateFlow(CornerSetting())
    val flowAllCorner: StateFlow<CornerSetting>
        get() = _flowAllCorner.asStateFlow()

    private val _flowSideCorner = MutableStateFlow(CornerSetting())
    val flowSideCorner: StateFlow<CornerSetting>
        get() = _flowSideCorner.asStateFlow()

    init {
        getSettingsPurchase()
    }

    fun onSaveSettingsPurchaseChanged() = viewModelScope.launchOnError {
        setSettingUseCase(flowPurchaseSetting.value)
        router().popBackStack()
    }

    fun onIsSymmetryChanged(isSymmetry: Boolean) = onSettingsPurchaseChanged { settingsPurchase ->
        settingsPurchase.copy(isSymmetry = isSymmetry)
    }

    fun onIsShowImageChanged(isShowImage: Boolean) = onSettingsPurchaseChanged { settingsPurchase ->
        settingsPurchase.copy(isImage = isShowImage)
    }

    fun onAllCornerChanged(size: Float) = viewModelScope.launchOnError {
        _flowAllCorner.emit(CornerSetting(size))
        onSettingsPurchaseChanged()
    }

    fun onSideCornerChanged(
        topStart: Float = flowSideCorner.value.topStart,
        topEnd: Float = flowSideCorner.value.topEnd,
        bottomStart: Float = flowSideCorner.value.bottomStart,
        bottomEnd: Float = flowSideCorner.value.bottomEnd
    ) = viewModelScope.launchOnError {
        _flowSideCorner.emit(CornerSetting(topStart, topEnd, bottomStart, bottomEnd))
        onSettingsPurchaseChanged()
    }

    fun onSizeTypeChanged(sizeType: SizeType) = onSettingsPurchaseChanged { settingsPurchase ->
        settingsPurchase.copy(sizeType = sizeType)
    }

    fun onTypeShapeChanged(shapeType: ShapeType) = onSettingsPurchaseChanged { settingsPurchase ->
        settingsPurchase.copy(shapeType = shapeType)
    }

    private fun onSettingsPurchaseChanged(
        purchaseSetting: suspend (PurchaseSetting) -> PurchaseSetting = { item -> item }
    ) = viewModelScope.launchOnError {
        val item = purchaseSetting(flowPurchaseSetting.value).setCorner(
            allCorner = flowAllCorner.value,
            sideCorner = flowSideCorner.value
        )
        _flowPurchaseSetting.emit(item)
    }

    private fun getSettingsPurchase() = getSettingUseCase().onEach { setting ->
        _flowPurchaseSetting.emit(setting)
        when (setting.isSymmetry) {
            true -> _flowAllCorner.emit(setting.toCornerSetting())
            false -> _flowSideCorner.emit(setting.toCornerSetting())
        }
    }.launchIn(viewModelScope)
}
