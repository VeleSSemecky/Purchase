package com.veles.purchase.domain.usecase.biometric

import com.veles.purchase.domain.repository.auth.BiometricRepository
import javax.crypto.Cipher
import javax.inject.Inject

class EncryptionUseCase @Inject constructor(
    private val biometricRepository: BiometricRepository
) {

    fun getCryptoObjectForEncryption(): Cipher? =
        biometricRepository.getCryptoObjectForEncryption()

    @Throws(IllegalArgumentException::class)
    fun encryptAndSaveData(plaintext: String, cipher: Cipher?) {
        if (cipher == null) throw IllegalArgumentException("Cipher must be notnull")
        val ciphertextWrapper = biometricRepository.encryptData(plaintext, cipher)
        biometricRepository.setCiphertextWrapper(ciphertextWrapper)
    }
}
