package com.veles.purchase.domain.usecase.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.firebase.storage.FirebaseStorageRepository
import javax.inject.Inject

class FirebaseStorageUseCase @Inject constructor(
    private val firebaseStorageRepository: FirebaseStorageRepository
) {

    suspend fun apiFirebaseStorage(list: List<PurchasePhotoModel>): Boolean =
        firebaseStorageRepository.apiFirebaseStorage(list)
}
