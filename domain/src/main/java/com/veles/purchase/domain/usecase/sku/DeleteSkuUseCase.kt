package com.veles.purchase.domain.usecase.sku

import com.veles.purchase.domain.repository.sku.SkuRepository
import javax.inject.Inject

class DeleteSkuUseCase @Inject constructor(
    private val skuRepository: SkuRepository
) {

    suspend operator fun invoke(id: String) = skuRepository.delete(id)
}
