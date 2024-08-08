package com.veles.purchase.data.repository.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.domain.repository.auth.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val credentialManager: CredentialManager,
    private val context: Context
) : LoginRepository {

    override suspend fun getGoogleIdToken(): String {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(EnvironmentConfig.SERVER_CLIENT_ID)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
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
