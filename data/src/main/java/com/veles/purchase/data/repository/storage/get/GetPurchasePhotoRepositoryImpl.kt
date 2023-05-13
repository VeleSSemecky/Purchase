package com.veles.purchase.data.repository.storage.get

import com.google.firebase.storage.FirebaseStorage
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.purchase.GetPurchasePhotoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPurchasePhotoRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : GetPurchasePhotoRepository {

    override fun getPhoto(purchasePhotoModel: PurchasePhotoModel): Any =
        when (purchasePhotoModel.status) {
            PhotoStatus.LOCAL -> purchasePhotoModel.purchasePhotoUri
            PhotoStatus.DOWNLOADED ->
                storage.reference
                    .child(purchasePhotoModel.purchaseId)
                    .child(purchasePhotoModel.purchasePhotoId)
        }
}
