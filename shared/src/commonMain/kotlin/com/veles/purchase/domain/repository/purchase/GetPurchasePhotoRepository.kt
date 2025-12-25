package com.veles.purchase.domain.repository.purchase

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel

interface GetPurchasePhotoRepository {

    fun getPhoto(purchasePhotoModel: PurchasePhotoModel): Any
}
