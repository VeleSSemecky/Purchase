package com.veles.purchase.data.model.user

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import com.veles.purchase.domain.model.user.UserPurchaseModel
import com.veles.purchase.domain.utill.dashString
import com.veles.purchase.domain.utill.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPurchaseModelData(
    val uid: String = emptyString(),
    val providerId: String = emptyString(),
    val displayName: String? = dashString(),
    val email: String? = dashString(),
    val phoneNumber: String? = dashString(),
    val fcmToken: String? = emptyString(),
    val photoUrl: String? = emptyString()
) : Parcelable

fun FirebaseUser.createUserPurchase(): UserPurchaseModelData =
    UserPurchaseModelData(
        uid = uid,
        providerId = providerId,
        displayName = displayName,
        email = email,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl.toString()
    )

fun UserPurchaseModelData.toUserPurchaseModel() = UserPurchaseModel(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)

fun UserPurchaseModel.toUserPurchaseModelData() = UserPurchaseModelData(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoUrl
)
