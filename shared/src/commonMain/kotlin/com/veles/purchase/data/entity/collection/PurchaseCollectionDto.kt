package com.veles.purchase.data.entity.collection

import com.veles.purchase.data.entity.purchase.PurchaseCategoryDto
import com.veles.purchase.data.entity.purchase.toPurchaseCategoryDto
import com.veles.purchase.data.entity.purchase.toPurchaseCategoryModel
import com.veles.purchase.data.entity.user.UserDto
import com.veles.purchase.data.entity.user.toUserPurchaseModel
import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.utill.emptyString
import kotlinx.serialization.Serializable

/**
 * Collection Data Transfer Object for Firebase KMP
 * Maps to Firestore collection document
 */
@Serializable
data class PurchaseCollectionDto(
    val id: String = emptyString(),
    val name: String = emptyString(),
    val creator: UserDto? = null,
    val listMembers: List<String> = emptyList(),
    val categoryModels: List<PurchaseCategoryDto> = emptyList()
)

/**
 * Convert CollectionDto to domain model
 */
fun PurchaseCollectionDto.toPurchaseCollectionModel() = PurchaseCollectionModel(
    id = id,
    name = name,
    creator = creator?.toUserPurchaseModel() ?: UserPurchaseModel.EMPTY,
    listMembers = listMembers,
    categoryModels = categoryModels.map { it.toPurchaseCategoryModel() }
)

/**
 * Convert domain model to CollectionDto
 */
fun PurchaseCollectionModel.toPurchaseCollectionDto() = PurchaseCollectionDto(
    id = id,
    name = name,
    creator = creator.let {
        UserDto(
            uid = it.uid,
            providerId = it.providerId,
            displayName = it.displayName,
            email = it.email,
            phoneNumber = it.phoneNumber,
            fcmToken = it.fcmToken,
            photoUrl = it.photoUrl
        )
    },
    listMembers = listMembers,
    categoryModels = categoryModels.map { it.toPurchaseCategoryDto() }
)
