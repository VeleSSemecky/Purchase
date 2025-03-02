package com.veles.purchase.data.repository.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.veles.purchase.domain.repository.auth.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth
) : LogoutRepository {

    override suspend fun logout() {
        CredentialManager.create(context).clearCredentialState(ClearCredentialStateRequest())
        firebaseAuth.signOut()
    }
}
