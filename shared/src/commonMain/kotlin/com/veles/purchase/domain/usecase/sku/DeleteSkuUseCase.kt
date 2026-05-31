package com.veles.purchase.domain.usecase.sku
import com.veles.purchase.domain.repository.sku.SkuRepository
import kotlinx.coroutines.CancellationException
class DeleteSkuUseCase(private val skuRepository: SkuRepository) {
    suspend operator fun invoke(id: String): Result<Unit> =
        runCatching { skuRepository.delete(id) }
            .also { it.exceptionOrNull()?.let { e -> if (e is CancellationException) throw e } }
}
