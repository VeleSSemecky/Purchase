package com.veles.purchase.presentation.presentation.mvvm.purchase.biometric

import androidx.lifecycle.ViewModel
import com.veles.purchase.domain.usecase.biometric.DecryptionUseCase
import com.veles.purchase.domain.usecase.biometric.EncryptionUseCase
import javax.inject.Inject

class BiometricComposeViewModel @Inject constructor(
    val encryptionUseCase: EncryptionUseCase,
    val decryptionUseCase: DecryptionUseCase
) : ViewModel()
