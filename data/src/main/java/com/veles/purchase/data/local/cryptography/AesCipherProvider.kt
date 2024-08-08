package com.veles.purchase.data.local.cryptography

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Named

class AesCipherProvider @Inject constructor(
    @Named(KEY_NAME) private val keyName: String,
    private val keyStore: KeyStore,
    @Named(KEY_STORE_NAME) private val keyStoreName: String,
    private val cipher: Cipher,
) {

    val encryptCipher: Cipher
        get() = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        }

    fun decryptCipher(iv: ByteArray): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getOrCreateKey(), IvParameterSpec(iv))
        }

    private val cipherMY by lazy {
        Cipher.getInstance(TRANSFORMATION)
    }


    fun encryptData(text: String): Pair<ByteArray, ByteArray> {
        cipherMY.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        return cipherMY.doFinal(text.toByteArray(charset("UTF-8"))) to cipherMY.iv
    }

    fun decryptData(encryptedData: ByteArray, cipherIV: ByteArray? = null): String {
//        cipherMY.init(Cipher.DECRYPT_MODE, getOrCreateKey(), IvParameterSpec(cipherMY.iv))
        cipherMY.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(128, cipherIV ?: cipherMY.iv))
        return cipherMY.doFinal(encryptedData).toString(charset("UTF-8"))
    }

    private fun generateSecretKey(): SecretKey {
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, keyStoreName).apply {
                KeyGenParameterSpec
                    .Builder(keyName, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
        }.generateKey()
    }

    private fun getOrCreateKey(): SecretKey =
        (keyStore.getEntry(keyName, null) as? KeyStore.SecretKeyEntry)?.secretKey
            ?: generateKey()

    private fun generateKey(): SecretKey =
        KeyGenerator.getInstance(ALGORITHM, keyStoreName)
            .apply { init(keyGenParams) }
            .generateKey()

    private val keyGenParams =
        KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(BLOCK_MODE)
            setEncryptionPaddings(PADDING)
            setUserAuthenticationRequired(false)
            setRandomizedEncryptionRequired(true)
        }.build()

    companion object {
        const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
//        const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
//        const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

        const val KEY_NAME = "Key Name"
        const val KEY_STORE_NAME = "Key Store Name"
    }
}
