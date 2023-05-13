package com.veles.purchase.data.core.extensions

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listenerRegistration = addSnapshotListener { snapshot, error ->
        when (snapshot != null && error == null) {
            true -> trySendBlocking(snapshot)
            false -> close(error)
        }
    }
    awaitClose {
        listenerRegistration.remove()
    }
}

fun <T> T.toUnit() = let { }
