package com.veles.purchase.data.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.FirebaseStorage
import dev.gitlive.firebase.storage.storage
import org.koin.dsl.module

/**
 * Firebase services module
 * Provides Firebase instances for dependency injection
 *
 * Requires FirebaseInitializer to be initialized first
 */
val firebaseModule = module {

    // Firebase Initializer (provided by platformModule)
    // Already initialized as singleton in platform modules

    // Firebase Auth
    single<FirebaseAuth> {
        Firebase.auth
    }

    // Firebase Firestore
    single<FirebaseFirestore> {
        Firebase.firestore
    }

    // Firebase Storage
    single<FirebaseStorage> {
        Firebase.storage
    }
}
