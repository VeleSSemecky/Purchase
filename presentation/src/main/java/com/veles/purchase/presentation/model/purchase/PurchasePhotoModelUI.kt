package com.veles.purchase.presentation.model.purchase

import android.net.Uri
import android.os.Parcelable
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.domain.utill.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchasePhotoModelUI(
    val purchaseId: String = emptyString(),
    val purchasePhotoId: String = createPrimaryIDKey(),
    val purchasePhotoUri: String = Uri.EMPTY.toString(),
    val status: PhotoStatus = PhotoStatus.LOCAL
) : Parcelable

fun PurchasePhotoModelUI.toPurchasePhotoModel() = PurchasePhotoModel(
    purchaseId = purchaseId,
    purchasePhotoId = purchasePhotoId,
    purchasePhotoUri = purchasePhotoUri,
    status = status
)

fun PurchasePhotoModel.toPurchasePhotoModelUI() = PurchasePhotoModelUI(
    purchaseId = purchaseId,
    purchasePhotoId = purchasePhotoId,
    purchasePhotoUri = purchasePhotoUri,
    status = status
)
