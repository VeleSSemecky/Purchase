package com.veles.purchase.domain.repository.auth

import com.veles.purchase.domain.model.user.UserPurchaseModel

interface AuthWithGoogleRepository {

    suspend fun firebaseAuthWithGoogle(idToken: String?): Boolean

    suspend fun apiFirebaseFirestore(userInfo: UserPurchaseModel): Boolean
}
