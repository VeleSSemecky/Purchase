package com.veles.purchase.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.data.extensions.userPurchase
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.data.networking.entity.user.UserDto
import com.veles.purchase.data.networking.entity.user.toUserDto
import com.veles.purchase.domain.core.suspendCancellableCoroutineWithTimeout
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthWithGoogleRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val dataStore: DataStore
) : AuthWithGoogleRepository {

    override suspend fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        val user: FirebaseUser = authResult.user ?: throw IllegalArgumentException("FirebaseUser is null")
        setUser(user.toUserDto(dataStore.getFCMToken()))
    }

    private suspend fun setUser(userDto: UserDto) = suspendCancellableCoroutineWithTimeout {
        firebaseFirestore.userPurchase
            .document(userDto.uid)
            .set(userDto)
            .await()
    }
}
