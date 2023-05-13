package com.veles.purchase.data.repository.later

import com.google.firebase.firestore.FirebaseFirestore
import com.veles.purchase.domain.repository.purchase.PurchaseLaterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PurchaseLaterRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PurchaseLaterRepository
