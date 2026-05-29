package com.veles.purchase.presentation.mvvm.purchase.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import com.veles.purchase.domain.usecase.setting.GetSettingUseCase
import com.veles.purchase.domain.usecase.setting.SetSettingUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for Purchase Settings screen
 * Migrated from presentation module - original name: SettingPurchaseComposeViewModel
 */
class SettingPurchaseComposeViewModel(private val getSettingUseCase: GetSettingUseCase, private val setSettingUseCase: SetSettingUseCase) :
    ViewModel() {

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
        loadSettings()
    }

    fun onSaveSettingsClicked() = viewModelScope.launch {
        try {
            setSettingUseCase(flowPurchaseSetting.value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onIsSymmetryChanged(isSymmetry: Boolean) = updateSettings { settings ->
        settings.copy(isSymmetry = isSymmetry)
    }

    fun onIsShowImageChanged(isShowImage: Boolean) = updateSettings { settings ->
        settings.copy(isImage = isShowImage)
    }

    fun onAllCornerChanged(size: Float) = viewModelScope.launch {
        _flowAllCorner.emit(CornerSetting(size))
        updateSettingsWithCorners()
    }

    fun onSideCornerChanged(
        topStart: Float = flowSideCorner.value.topStart,
        topEnd: Float = flowSideCorner.value.topEnd,
        bottomStart: Float = flowSideCorner.value.bottomStart,
        bottomEnd: Float = flowSideCorner.value.bottomEnd
    ) = viewModelScope.launch {
        _flowSideCorner.emit(CornerSetting(topStart, topEnd, bottomStart, bottomEnd))
        updateSettingsWithCorners()
    }

    fun onSizeTypeChanged(sizeType: SizeType) = updateSettings { settings ->
        settings.copy(sizeType = sizeType)
    }

    fun onShapeTypeChanged(shapeType: ShapeType) = updateSettings { settings ->
        settings.copy(shapeType = shapeType)
    }

    private fun updateSettings(
        transform: (PurchaseSetting) -> PurchaseSetting
    ) = viewModelScope.launch {
        val updated = transform(flowPurchaseSetting.value).applyCorners(
            allCorner = flowAllCorner.value,
            sideCorner = flowSideCorner.value
        )
        _flowPurchaseSetting.emit(updated)
    }

    private fun updateSettingsWithCorners() = viewModelScope.launch {
        val updated = flowPurchaseSetting.value.applyCorners(
            allCorner = flowAllCorner.value,
            sideCorner = flowSideCorner.value
        )
        _flowPurchaseSetting.emit(updated)
    }

    private fun loadSettings() = getSettingUseCase()
        .onEach { settings ->
            _flowPurchaseSetting.emit(settings)
            when (settings.isSymmetry) {
                true -> _flowAllCorner.emit(settings.toCornerSetting())
                false -> _flowSideCorner.emit(settings.toCornerSetting())
            }
        }
        .launchIn(viewModelScope)
}

data class CornerSetting(val topStart: Float = 0f, val topEnd: Float = 0f, val bottomStart: Float = 0f, val bottomEnd: Float = 0f) {
    constructor(allCorners: Float) : this(allCorners, allCorners, allCorners, allCorners)
}

private fun PurchaseSetting.toCornerSetting() = CornerSetting(
    topStart = topStart,
    topEnd = topEnd,
    bottomStart = bottomStart,
    bottomEnd = bottomEnd
)

private fun PurchaseSetting.applyCorners(
    allCorner: CornerSetting,
    sideCorner: CornerSetting
): PurchaseSetting = if (isSymmetry) {
    copy(
        topStart = allCorner.topStart,
        topEnd = allCorner.topEnd,
        bottomStart = allCorner.bottomStart,
        bottomEnd = allCorner.bottomEnd
    )
} else {
    copy(
        topStart = sideCorner.topStart,
        topEnd = sideCorner.topEnd,
        bottomStart = sideCorner.bottomStart,
        bottomEnd = sideCorner.bottomEnd
    )
}
