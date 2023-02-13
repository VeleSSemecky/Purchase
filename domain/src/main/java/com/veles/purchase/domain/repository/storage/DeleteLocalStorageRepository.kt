package com.veles.purchase.domain.repository.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel

interface DeleteLocalStorageRepository {
    suspend fun deletePhoto(purchaseModel: PurchasePhotoModel): Boolean
}
