package com.veles.purchase.presentation.data.result

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.veles.purchase.config.EnvironmentConfig

object GoogleSignIn {

    suspend operator fun invoke(context: Activity): String {
        val googleIdOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(EnvironmentConfig.SERVER_CLIENT_ID)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = CredentialManager.create(context).getCredential(
                request = request,
                context = context
            )
            return handleSignIn(result)
        } catch (e: GetCredentialException) {
            throw e
        }
    }

    private fun handleSignIn(result: GetCredentialResponse): String {
        when (val credential = result.credential) {
            is CustomCredential -> if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    return GoogleIdTokenCredential.createFrom(credential.data).idToken
                } catch (e: GoogleIdTokenParsingException) {
                    throw e
                }
            } else {
                throw IllegalStateException("Unexpected type of credential")
            }

            else -> throw IllegalStateException("Unexpected type of credential")
        }
    }
}
