package com.veles.purchase.domain.usecase.sku
import com.veles.purchase.domain.repository.sku.SkuRepository
class GetSkuSumMontUseCase(private val skuRepository: SkuRepository) {
    suspend operator fun invoke(year: Int, month: Int) =
        skuRepository.getSkuSumMonthList(year, month)
}
