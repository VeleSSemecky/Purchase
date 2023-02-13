package com.veles.purchase.domain.repository.firebase.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel

interface FirebaseStorageRepository {

    suspend fun apiFirebaseStorage(list: List<PurchasePhotoModel>): Boolean

    suspend fun apiFirebaseStorageDelete(purchaseCreateId: String): Boolean

    fun getStoragePhoto(purchasePhotoModel: PurchasePhotoModel): Any
}
