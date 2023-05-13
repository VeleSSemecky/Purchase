package com.veles.purchase.domain.usecase.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.purchase.GetPurchasePhotoRepository
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val getPurchasePhotoRepository: GetPurchasePhotoRepository
) {

    operator fun invoke(purchasePhotoModel: PurchasePhotoModel): Any =
        getPurchasePhotoRepository.getPhoto(purchasePhotoModel)
}
