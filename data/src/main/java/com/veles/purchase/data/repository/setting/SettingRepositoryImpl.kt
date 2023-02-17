package com.veles.purchase.data.repository.setting

import com.veles.purchase.data.local.store.SettingsDataStore
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.repository.setting.SettingRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingRepository {

    override fun getFlowSettingsPurchase(): Flow<PurchaseSetting> =
        settingsDataStore.flowPurchaseSetting

    override suspend fun saveSettingsPurchase(
        purchaseSetting: PurchaseSetting
    ) = settingsDataStore.saveSettingsPurchase(purchaseSetting)
}
