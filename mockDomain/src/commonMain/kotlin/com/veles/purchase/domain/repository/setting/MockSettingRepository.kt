package com.veles.purchase.domain.repository.setting

import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockSettingRepository : SettingRepository {

    private val settings = MutableStateFlow(
        PurchaseSetting(
            sizeType = SizeType.DP,
            shapeType = ShapeType.ROUNDED,
            topStart = 16f,
            topEnd = 16f,
            bottomEnd = 16f,
            bottomStart = 16f,
            isImage = true,
            isSymmetry = true
        )
    )

    override fun getFlowSettingsPurchase(): Flow<PurchaseSetting> {
        return settings.asStateFlow()
    }

    override suspend fun saveSettingsPurchase(purchaseSetting: PurchaseSetting) {
        delay(200)
        settings.value = purchaseSetting
    }
}
