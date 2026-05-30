package com.veles.purchase.data.repository.purchase

import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.purchase.GetPurchasePhotoRepository

class GetPurchasePhotoRepositoryImpl : GetPurchasePhotoRepository {

    /**
     * Returns the data source for Coil3 to load an image:
     * - LOCAL photo: returns ByteArray directly
     * - DOWNLOADED photo: returns the Firebase Storage URL string
     */
    override fun getPhoto(purchasePhotoModel: PurchasePhotoModel): Any =
        when (purchasePhotoModel.status) {
            PhotoStatus.LOCAL -> purchasePhotoModel.bytes ?: ByteArray(0)
            PhotoStatus.DOWNLOADED -> purchasePhotoModel.purchasePhotoUri
        }
}
