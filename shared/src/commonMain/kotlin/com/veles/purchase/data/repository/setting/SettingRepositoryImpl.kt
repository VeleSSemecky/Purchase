package com.veles.purchase.data.repository.setting

import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import com.veles.purchase.domain.repository.setting.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory implementation of SettingRepository
 * TODO Phase 6.1: Migrate to DataStore KMP for persistence
 */
class SettingRepositoryImpl : SettingRepository {

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

    override fun getFlowSettingsPurchase(): Flow<PurchaseSetting> = settings.asStateFlow()

    override suspend fun saveSettingsPurchase(purchaseSetting: PurchaseSetting) {
        settings.value = purchaseSetting
    }
}
