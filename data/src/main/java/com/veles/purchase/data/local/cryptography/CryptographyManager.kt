package com.veles.purchase.data.local.cryptography

import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.google.gson.Gson
import com.veles.purchase.config.EnvironmentConfig.ANDROID_KEYSTORE
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_BLOCK_MODE
import com.veles.purchase.config.EnvironmentConfig.ENCRYPTION_PADDING
import com.veles.purchase.config.EnvironmentConfig.KEY_SIZE
import com.veles.purchase.domain.model.cryptography.CiphertextWrapper
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class CryptographyManager @Inject constructor(
    private val cipher: Cipher,
    private val sharedPreferences: SharedPreferences
) {

    fun getInitializedCipherForEncryption(keyName: String): Cipher {
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    fun getInitializedCipherForDecryption(
        keyName: String,
        initializationVector: ByteArray
    ): Cipher {
        val secretKey = getOrCreateSecretKey(keyName)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, initializationVector))
        return cipher
    }

    fun encryptData(plaintext: String, cipher: Cipher): CiphertextWrapper {
        val ciphertext = cipher.doFinal(plaintext.toByteArray(Charset.forName("UTF-8")))
        return CiphertextWrapper(ciphertext, cipher.iv)
    }

    fun decryptData(ciphertext: ByteArray, cipher: Cipher): String {
        val plaintext = cipher.doFinal(ciphertext)
        return String(plaintext, Charset.forName("UTF-8"))
    }

    fun setCiphertextWrapper(
        ciphertextWrapper: CiphertextWrapper
    ) {
        val json = Gson().toJson(ciphertextWrapper)
        sharedPreferences.edit().putString(CIPHERTEXT_WRAPPER_KEY, json).apply()
    }

    fun getCiphertextWrapper(): CiphertextWrapper {
        val json = sharedPreferences.getString(CIPHERTEXT_WRAPPER_KEY, null)
            ?: return CiphertextWrapper.EMPTY
        return Gson().fromJson(json, CiphertextWrapper::class.java)
    }

    private fun getOrCreateSecretKey(keyName: String): SecretKey {
        // If Secretkey was previously created for that keyName, then grab and return it.
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed
        keyStore.getKey(keyName, null)?.let { return it as SecretKey }

        // if you reach here, then a new SecretKey must be generated for that keyName
        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true)
        }

        val keyGenParams = paramsBuilder.build()
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )
        keyGenerator.init(keyGenParams)
        return keyGenerator.generateKey()
    }

    companion object {
        private const val CIPHERTEXT_WRAPPER_KEY = "CIPHERTEXT_WRAPPER_KEY"
    }
}
