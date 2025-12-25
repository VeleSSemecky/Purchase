package com.veles.purchase.data.repository.auth

import com.veles.purchase.data.entity.user.UserDto
import com.veles.purchase.data.extensions.toUserDto
import com.veles.purchase.data.extensions.userPurchase
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.firestore.FirebaseFirestore

/**
 * Firebase KMP implementation of AuthWithGoogleRepository
 * Handles Google Sign-In authentication
 */
class AuthWithGoogleRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val getFCMToken: suspend () -> String = { "" }
) : AuthWithGoogleRepository {

    override suspend fun firebaseAuthWithGoogle(idToken: String?) {
        if (idToken == null) {
            throw IllegalArgumentException("ID Token cannot be null")
        }

        // Create Google credential
        val credential = GoogleAuthProvider.credential(idToken, null)

        // Sign in with credential
        val authResult = auth.signInWithCredential(credential)

        // Get user from result
        val user = authResult.user
            ?: throw IllegalArgumentException("FirebaseUser is null")

        // Save user to Firestore
        val fcmToken = getFCMToken()
        setUser(user.toUserDto(fcmToken))
    }

    private suspend fun setUser(userDto: UserDto) {
        firestore.userPurchase
            .document(userDto.uid)
            .set(userDto)
    }
}

