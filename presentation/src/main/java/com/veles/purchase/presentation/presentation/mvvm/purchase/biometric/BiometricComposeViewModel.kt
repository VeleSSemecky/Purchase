package com.veles.purchase.presentation.presentation.mvvm.purchase.biometric

import androidx.lifecycle.ViewModel
import com.veles.purchase.domain.usecase.biometric.DecryptionUseCase
import com.veles.purchase.domain.usecase.biometric.EncryptionUseCase

class BiometricComposeViewModel(
    val encryptionUseCase: EncryptionUseCase,
    val decryptionUseCase: DecryptionUseCase
) : ViewModel()
