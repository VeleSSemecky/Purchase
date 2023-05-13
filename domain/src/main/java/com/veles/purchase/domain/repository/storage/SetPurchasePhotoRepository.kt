package com.veles.purchase.domain.repository.storage

import com.veles.purchase.domain.model.purchase.PurchasePhotoModel

interface SetPurchasePhotoRepository {

    suspend fun setPurchasePhotos(list: List<PurchasePhotoModel>)
}
