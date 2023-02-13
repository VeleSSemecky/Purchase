package com.veles.purchase.domain.usecase.biometric

import com.veles.purchase.domain.repository.auth.BiometricRepository
import javax.crypto.Cipher
import javax.inject.Inject

class DecryptionUseCase @Inject constructor(
    private val biometricRepository: BiometricRepository
) {

    fun getCiphertextWrapper() = biometricRepository.getCiphertextWrapper()

    fun getCryptoObjectForDecryption(initializationVector: ByteArray): Cipher? =
        biometricRepository.getCryptoObjectForDecryption(initializationVector)

    @Throws(IllegalArgumentException::class)
    fun decryptData(cipher: Cipher?): String {
        if (cipher == null) throw IllegalArgumentException("Cipher must be notnull")
        return biometricRepository.decryptData(getCiphertextWrapper().ciphertext, cipher)
    }
}
