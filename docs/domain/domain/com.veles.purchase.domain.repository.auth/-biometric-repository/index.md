//[domain](../../../index.md)/[com.veles.purchase.domain.repository.auth](../index.md)/[BiometricRepository](index.md)

# BiometricRepository

[jvm]\
interface [BiometricRepository](index.md)

## Functions

| Name | Summary |
|---|---|
| [decryptData](decrypt-data.md) | [jvm]<br>abstract fun [decryptData](decrypt-data.md)(ciphertext: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), cipher: [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [encryptData](encrypt-data.md) | [jvm]<br>abstract fun [encryptData](encrypt-data.md)(plaintext: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cipher: [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)): [CiphertextWrapper](../../com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getCiphertextWrapper](get-ciphertext-wrapper.md) | [jvm]<br>abstract fun [getCiphertextWrapper](get-ciphertext-wrapper.md)(): [CiphertextWrapper](../../com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getCryptoObjectForDecryption](get-crypto-object-for-decryption.md) | [jvm]<br>abstract fun [getCryptoObjectForDecryption](get-crypto-object-for-decryption.md)(initializationVector: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)): [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)? |
| [getCryptoObjectForEncryption](get-crypto-object-for-encryption.md) | [jvm]<br>abstract fun [getCryptoObjectForEncryption](get-crypto-object-for-encryption.md)(): [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)? |
| [setCiphertextWrapper](set-ciphertext-wrapper.md) | [jvm]<br>abstract fun [setCiphertextWrapper](set-ciphertext-wrapper.md)(ciphertextWrapper: [CiphertextWrapper](../../com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md)) |
