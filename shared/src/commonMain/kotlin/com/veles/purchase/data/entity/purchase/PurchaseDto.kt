package com.veles.purchase.data.entity.purchase

import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.utill.emptyString
import com.veles.purchase.domain.utill.createPrimaryIDKey
import kotlinx.serialization.Serializable

/**
 * Purchase Data Transfer Object for Firebase KMP
 * Maps to Firestore purchase document
 */
@Serializable
data class PurchaseDto(
    val createId: String = "",
    val text: String = emptyString(),
    val count: String = emptyString(),
    val checked: Boolean = false,
    val price: String = emptyString(),
    val userList: List<String> = emptyList(),
    val listImage: List<PurchasePhotoDto> = emptyList(),
    val purchaseCategoryDto: PurchaseCategoryDto? = null
)

@Serializable
data class PurchasePhotoDto(
    val purchaseId: String = emptyString(),
    val purchasePhotoId: String = emptyString(),
    val purchasePhotoUri: String = emptyString(),
    val status: String = "LOCAL"
)

@Serializable
data class PurchaseCategoryDto(
    val id: String = emptyString(),
    val name: String = emptyString()
)

/**
 * Convert PurchaseDto to domain model
 */
fun PurchaseDto.toPurchaseModel() = PurchaseModel(
    createId = createId,
    text = text,
    count = count,
    isChecked = checked,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoModel() },
    purchaseCategoryModel = purchaseCategoryDto?.toPurchaseCategoryModel()
)

/**
 * Convert domain model to PurchaseDto
 */
fun PurchaseModel.toPurchaseDto() = PurchaseDto(
    createId = createId,
    text = text,
    count = count,
    checked = isChecked,
    price = price,
    userList = userList,
    listImage = listImage.map { it.toPurchasePhotoDto() },
    purchaseCategoryDto = purchaseCategoryModel?.toPurchaseCategoryDto()
)

fun PurchasePhotoDto.toPurchasePhotoModel() = PurchasePhotoModel(
    purchaseId = purchaseId,
    purchasePhotoId = purchasePhotoId.ifEmpty { createPrimaryIDKey() },
    purchasePhotoUri = purchasePhotoUri,
    status = when (status) {
        "DOWNLOADED" -> PhotoStatus.DOWNLOADED
        else -> PhotoStatus.LOCAL
    }
)

fun PurchasePhotoModel.toPurchasePhotoDto() = PurchasePhotoDto(
    purchaseId = purchaseId,
    purchasePhotoId = purchasePhotoId,
    purchasePhotoUri = purchasePhotoUri,
    status = status.name
)

fun PurchaseCategoryDto.toPurchaseCategoryModel() = PurchaseCategoryModel(
    id = id,
    name = name
)

fun PurchaseCategoryModel.toPurchaseCategoryDto() = PurchaseCategoryDto(
    id = id,
    name = name
)

