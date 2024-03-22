//[data](../../../index.md)/[com.veles.purchase.data.repository.auth](../index.md)/[BiometricRepositoryImpl](index.md)

# BiometricRepositoryImpl

[androidJvm]\
class [BiometricRepositoryImpl](index.md)@Injectconstructor(cryptographyManager: [CryptographyManager](../../com.veles.purchase.data.local.cryptography/-cryptography-manager/index.md)) : [BiometricRepository](../../../../domain/domain/com.veles.purchase.domain.repository.auth/-biometric-repository/index.md)

## Constructors

| | |
|---|---|
| [BiometricRepositoryImpl](-biometric-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(cryptographyManager: [CryptographyManager](../../com.veles.purchase.data.local.cryptography/-cryptography-manager/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [decryptData](decrypt-data.md) | [androidJvm]<br>open override fun [decryptData](decrypt-data.md)(ciphertext: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), cipher: [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [encryptData](encrypt-data.md) | [androidJvm]<br>open override fun [encryptData](encrypt-data.md)(plaintext: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cipher: [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html)): [CiphertextWrapper](../../../../domain/domain/com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getCiphertextWrapper](get-ciphertext-wrapper.md) | [androidJvm]<br>open override fun [getCiphertextWrapper](get-ciphertext-wrapper.md)(): [CiphertextWrapper](../../../../domain/domain/com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getCryptoObjectForDecryption](get-crypto-object-for-decryption.md) | [androidJvm]<br>open override fun [getCryptoObjectForDecryption](get-crypto-object-for-decryption.md)(initializationVector: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)): [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html)? |
| [getCryptoObjectForEncryption](get-crypto-object-for-encryption.md) | [androidJvm]<br>open override fun [getCryptoObjectForEncryption](get-crypto-object-for-encryption.md)(): [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html)? |
| [setCiphertextWrapper](set-ciphertext-wrapper.md) | [androidJvm]<br>open override fun [setCiphertextWrapper](set-ciphertext-wrapper.md)(ciphertextWrapper: [CiphertextWrapper](../../../../domain/domain/com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md)) |
