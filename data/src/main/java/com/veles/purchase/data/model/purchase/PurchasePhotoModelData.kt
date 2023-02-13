package com.veles.purchase.data.model.purchase

import android.net.Uri
import android.os.Parcelable
import com.veles.purchase.data.room.core.createPrimaryIDKey
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.utill.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchasePhotoModelData(
    val purchaseId: String = emptyString(),
    val purchasePhotoId: String = createPrimaryIDKey(),
    val purchasePhotoUri: String = Uri.EMPTY.toString(),
    val status: PhotoStatus = PhotoStatus.LOCAL
) : Parcelable

fun PurchasePhotoModelData.toPurchasePhotoModel() = PurchasePhotoModel(
    purchaseId = purchaseId,
    purchasePhotoId = purchasePhotoId,
    purchasePhotoUri = purchasePhotoUri,
    status = status
)

fun PurchasePhotoModel.toPurchasePhotoModelData() = PurchasePhotoModelData(
    purchaseId = purchaseId,
    purchasePhotoId = purchasePhotoId,
    purchasePhotoUri = purchasePhotoUri,
    status = status
)
