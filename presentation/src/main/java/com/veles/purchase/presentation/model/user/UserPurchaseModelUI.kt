package com.veles.purchase.presentation.model.user

import android.os.Parcelable
import com.veles.purchase.domain.model.user.UserPurchaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPurchaseModelUI(
    val uid: String = "",
    val providerId: String = "",
    val displayName: String? = "-",
    val email: String? = "-",
    val phoneNumber: String? = "-",
    val fcmToken: String? = "",
    val photoUrl: String? = ""
) : Parcelable

fun UserPurchaseModelUI.toUserPurchaseModel() = UserPurchaseModel(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)

fun UserPurchaseModel.toUserPurchaseModelUI() = UserPurchaseModelUI(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)
