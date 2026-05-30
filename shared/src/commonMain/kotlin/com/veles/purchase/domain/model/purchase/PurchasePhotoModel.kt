package com.veles.purchase.domain.model.purchase

import com.veles.purchase.domain.utill.createPrimaryIDKey

data class PurchasePhotoModel(
    val purchaseId: String,
    val purchasePhotoId: String = createPrimaryIDKey(),
    val purchasePhotoUri: String,
    val status: PhotoStatus = PhotoStatus.LOCAL,
    val bytes: ByteArray? = null // in-memory only, never serialized to Firestore
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PurchasePhotoModel) return false
        return purchasePhotoId == other.purchasePhotoId
    }

    override fun hashCode(): Int = purchasePhotoId.hashCode()
}
