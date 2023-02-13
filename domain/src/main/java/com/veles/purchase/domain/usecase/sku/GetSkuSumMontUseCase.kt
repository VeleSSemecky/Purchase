package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.repository.sku.SkuRepository
import javax.inject.Inject

class GetSkuSumMontUseCase @Inject constructor(
    private val skuRepository: SkuRepository
) {

    suspend operator fun invoke(year: Int, month: Int) =
        skuRepository.getSkuSumMonthList(year, month)
}
