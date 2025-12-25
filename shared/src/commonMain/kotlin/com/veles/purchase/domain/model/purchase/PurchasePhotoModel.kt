package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.utill.createPrimaryIDKey

data class PurchasePhotoModel(
    val purchaseId: String,
    val purchasePhotoId: String = createPrimaryIDKey(),
    val purchasePhotoUri: String,
    val status: PhotoStatus = PhotoStatus.LOCAL
)
