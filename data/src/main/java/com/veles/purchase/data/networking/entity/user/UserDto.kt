package com.veles.purchase.data.networking.entity.user

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.utill.dashString
import com.veles.purchase.domain.utill.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto(
    val uid: String = emptyString(),
    val providerId: String = emptyString(),
    val displayName: String? = dashString(),
    val email: String? = dashString(),
    val phoneNumber: String? = dashString(),
    val fcmToken: String? = emptyString(),
    val photoUrl: String? = emptyString()
) : Parcelable

fun FirebaseUser.toUserDto(fcmToken:String = emptyString()): UserDto = UserDto(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl.toString()
)

fun UserDto.toUserPurchaseModel() = UserPurchaseModel(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)

fun UserPurchaseModel.toUserDto() = UserDto(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)
