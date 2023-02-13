package com.veles.purchase.domain.repository.auth

import com.veles.purchase.domain.model.cryptography.CiphertextWrapper
import javax.crypto.Cipher

interface BiometricRepository {
    fun getCryptoObjectForDecryption(initializationVector: ByteArray): Cipher?
    fun getCryptoObjectForEncryption(): Cipher?
    fun encryptData(plaintext: String, cipher: Cipher): CiphertextWrapper
    fun decryptData(ciphertext: ByteArray, cipher: Cipher): String
    fun setCiphertextWrapper(ciphertextWrapper: CiphertextWrapper)
    fun getCiphertextWrapper(): CiphertextWrapper
}
