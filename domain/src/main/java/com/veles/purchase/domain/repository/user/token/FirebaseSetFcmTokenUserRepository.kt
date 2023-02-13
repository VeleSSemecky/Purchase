package com.veles.purchase.domain.repository.user.token

import com.veles.purchase.domain.model.user.UserPurchaseModel

interface FirebaseSetFcmTokenUserRepository {
    suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean
}
