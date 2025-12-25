package com.veles.purchase.domain.model.user

data class UserPurchaseModel(
    val uid: String,
    val providerId: String,
    val displayName: String?,
    val email: String?,
    val phoneNumber: String?,
    val fcmToken: String?,
    val photoUrl: String?
) {

    companion object {

        val EMPTY = UserPurchaseModel(
            uid = "",
            providerId = "",
            displayName = "-",
            email = "-",
            phoneNumber = "",
            fcmToken = "",
            photoUrl = ""
        )
    }
}
