package com.veles.purchase.domain.usecase.setting

import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.repository.setting.SettingRepository
import kotlinx.coroutines.flow.Flow

class GetSettingUseCase(private val settingRepository: SettingRepository) {

    operator fun invoke(): Flow<PurchaseSetting> = settingRepository.getFlowSettingsPurchase()
}
