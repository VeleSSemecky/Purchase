package com.veles.purchase.data.repository.user.logout

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.veles.purchase.domain.repository.user.logout.UserLogoutRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class UserLogoutRepositoryImpl @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val firebaseAuth: FirebaseAuth
) : UserLogoutRepository {

    override suspend fun logout() {
        googleSignInClient.signOut().await()
        firebaseAuth.signOut()
    }
}
