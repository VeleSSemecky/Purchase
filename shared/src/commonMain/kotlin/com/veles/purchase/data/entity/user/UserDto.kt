package com.veles.purchase.data.entity.user

import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.utill.dashString
import com.veles.purchase.domain.utill.emptyString
import kotlinx.serialization.Serializable

/**
 * User Data Transfer Object for Firebase KMP
 * Maps to Firestore user document
 */
@Serializable
data class UserDto(
    val uid: String = emptyString(),
    val providerId: String = emptyString(),
    val displayName: String? = dashString(),
    val email: String? = dashString(),
    val phoneNumber: String? = dashString(),
    val fcmToken: String? = emptyString(),
    val photoUrl: String? = emptyString()
)

/**
 * Convert UserDto to domain model
 */
fun UserDto.toUserPurchaseModel() = UserPurchaseModel(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)

/**
 * Convert domain model to UserDto
 */
fun UserPurchaseModel.toUserDto() = UserDto(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)
