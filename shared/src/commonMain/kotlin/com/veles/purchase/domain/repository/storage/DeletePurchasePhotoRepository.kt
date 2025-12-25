package com.veles.purchase.domain.repository.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel

interface DeletePurchasePhotoRepository {

    suspend fun deletePhoto(purchasePhotoModel: PurchasePhotoModel)

    suspend fun deletePhotos(purchaseId: String, listImage: List<PurchasePhotoModel>)
}
