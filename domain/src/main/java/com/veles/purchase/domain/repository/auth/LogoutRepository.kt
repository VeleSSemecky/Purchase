package com.veles.purchase.domain.repository.auth

interface LogoutRepository {

    suspend fun logout()
}
