package com.veles.purchase.domain.repository.user.set

import com.veles.purchase.domain.model.user.UserPurchaseModel

interface FirebaseSetUserRepository {
    suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean
}
