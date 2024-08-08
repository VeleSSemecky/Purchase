package com.veles.purchase.data.networking.client

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.domain.repository.auth.AuthWithGoogleRepository
import com.veles.purchase.domain.repository.auth.LoginRepository
import javax.inject.Inject

class PurchaseClient @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
)
