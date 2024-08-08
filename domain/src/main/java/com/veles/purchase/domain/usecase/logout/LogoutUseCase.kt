package com.veles.purchase.domain.usecase.logout

import com.veles.purchase.domain.repository.auth.LogoutRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userLogoutRepository: LogoutRepository
) {

    suspend operator fun invoke() = userLogoutRepository.logout()
}
