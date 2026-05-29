package com.veles.purchase.platform.auth

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

/**
 * Android implementation of GoogleSignInHelper
 * Uses Credential Manager API for Google Sign-In
 */
actual class GoogleSignInHelper(private val activity: Activity, private val serverClientId: String) {
    actual suspend fun signIn(): Pair<String, String?> {
        val googleIdOption = GetSignInWithGoogleOption.Builder(serverClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(activity)

        val result = credentialManager.getCredential(
            request = request,
            context = activity
        )

        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    return googleIdTokenCredential.idToken to null
                } else {
                    throw IllegalStateException("Unexpected credential type")
                }
            }
            else -> {
                throw IllegalStateException("Unexpected credential type")
            }
        }
    }
}
