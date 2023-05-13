package com.veles.purchase.domain.usecase.logout

import com.veles.purchase.domain.repository.user.UserLogoutRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userLogoutRepository: UserLogoutRepository
) {

    suspend operator fun invoke() = userLogoutRepository.logout()
}
