package com.veles.purchase.domain.repository.setting

import com.veles.purchase.domain.model.setting.PurchaseSetting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getFlowSettingsPurchase(): Flow<PurchaseSetting>
    suspend fun saveSettingsPurchase(purchaseSetting: PurchaseSetting)
}
