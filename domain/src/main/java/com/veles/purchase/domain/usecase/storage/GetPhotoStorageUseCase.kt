package com.veles.purchase.domain.usecase.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.firebase.storage.FirebaseStorageRepository
import javax.inject.Inject

class GetPhotoStorageUseCase @Inject constructor(
    private val firebaseStorageRepository: FirebaseStorageRepository
) {

    operator fun invoke(purchasePhotoModel: PurchasePhotoModel): Any =
        firebaseStorageRepository.getStoragePhoto(purchasePhotoModel)
}
