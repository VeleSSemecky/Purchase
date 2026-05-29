package com.veles.purchase.domain.usecase.sku
import com.veles.purchase.domain.repository.sku.SkuRepository
class DeleteSkuUseCase(private val skuRepository: SkuRepository) {
    suspend operator fun invoke(id: String) = skuRepository.delete(id)
}
