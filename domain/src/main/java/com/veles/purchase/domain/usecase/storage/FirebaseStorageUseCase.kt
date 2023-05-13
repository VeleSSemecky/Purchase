package com.veles.purchase.domain.usecase.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository
import javax.inject.Inject

class FirebaseStorageUseCase @Inject constructor(
    private val setPurchasePhotoRepository: SetPurchasePhotoRepository
) {

    suspend fun apiFirebaseStorage(list: List<PurchasePhotoModel>) =
        setPurchasePhotoRepository.setPurchasePhotos(list)
}
