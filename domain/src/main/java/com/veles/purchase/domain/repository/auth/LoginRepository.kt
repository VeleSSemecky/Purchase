package com.veles.purchase.domain.repository.auth

interface LoginRepository {

    suspend fun getGoogleIdToken(): String
}
