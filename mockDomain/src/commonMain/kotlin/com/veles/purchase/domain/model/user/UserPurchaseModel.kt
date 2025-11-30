package com.veles.purchase.domain.model.user

/**
 * User model for purchase collections
 *
 * Original: /domain/src/main/java/com/veles/purchase/domain/model/user/UserPurchaseModel.kt
 * Adapted for KMP with Kotlin multiplatform types
 */
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

        val MOCK_USER = UserPurchaseModel(
            uid = "mock_user_123",
            providerId = "google.com",
            displayName = "John Doe",
            email = "john.doe@example.com",
            phoneNumber = "+380501234567",
            fcmToken = "mock_fcm_token",
            photoUrl = ""
        )
    }
}