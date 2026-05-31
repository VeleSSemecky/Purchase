package com.veles.purchase.domain.usecase.setting

import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.repository.setting.SettingRepository
import kotlinx.coroutines.CancellationException

class SetSettingUseCase(private val settingRepository: SettingRepository) {

    suspend operator fun invoke(purchaseSetting: PurchaseSetting): Result<Unit> =
        runCatching { settingRepository.saveSettingsPurchase(purchaseSetting) }
            .also { it.exceptionOrNull()?.let { e -> if (e is CancellationException) throw e } }
}
