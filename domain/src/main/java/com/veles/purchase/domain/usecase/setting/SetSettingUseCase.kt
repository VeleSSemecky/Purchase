package com.veles.purchase.domain.usecase.setting

import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SetSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {

    suspend operator fun invoke(purchaseSetting: PurchaseSetting) =
        settingRepository.saveSettingsPurchase(purchaseSetting)
}
