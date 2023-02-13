package com.veles.purchase.data.repository.auth

import android.os.Build
import com.veles.purchase.config.EnvironmentConfig.SECRET_KEY_NAME
import com.veles.purchase.data.local.cryptography.CryptographyManager
import com.veles.purchase.domain.model.cryptography.CiphertextWrapper
import com.veles.purchase.domain.repository.auth.BiometricRepository
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.inject.Inject

class BiometricRepositoryImpl @Inject constructor(
    private val cryptographyManager: CryptographyManager
) : BiometricRepository {

    override fun getCryptoObjectForDecryption(initializationVector: ByteArray): Cipher? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cryptographyManager.getInitializedCipherForDecryption(
                SECRET_KEY_NAME,
                initializationVector
            )
        } else {
            null
        }

    override fun getCryptoObjectForEncryption(): Cipher? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cryptographyManager.getInitializedCipherForEncryption(SECRET_KEY_NAME)
        } else {
            null
        }

    override fun encryptData(plaintext: String, cipher: Cipher): CiphertextWrapper {
        val ciphertext = cipher.doFinal(plaintext.toByteArray(Charset.forName("UTF-8")))
        return CiphertextWrapper(ciphertext, cipher.iv)
    }

    override fun decryptData(ciphertext: ByteArray, cipher: Cipher): String {
        val plaintext = cipher.doFinal(ciphertext)
        return String(plaintext, Charset.forName("UTF-8"))
    }

    override fun setCiphertextWrapper(ciphertextWrapper: CiphertextWrapper) =
        cryptographyManager.setCiphertextWrapper(ciphertextWrapper)

    override fun getCiphertextWrapper(): CiphertextWrapper = cryptographyManager.getCiphertextWrapper()
}
