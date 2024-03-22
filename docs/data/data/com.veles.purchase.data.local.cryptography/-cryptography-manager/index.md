//[data](../../../index.md)/[com.veles.purchase.data.local.cryptography](../index.md)/[CryptographyManager](index.md)

# CryptographyManager

[androidJvm]\
class [CryptographyManager](index.md)@Injectconstructor(cipher: [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html), sharedPreferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html))

## Constructors

| | |
|---|---|
| [CryptographyManager](-cryptography-manager.md) | [androidJvm]<br>@Inject<br>constructor(cipher: [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html), sharedPreferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [decryptData](decrypt-data.md) | [androidJvm]<br>fun [decryptData](decrypt-data.md)(ciphertext: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), cipher: [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [encryptData](encrypt-data.md) | [androidJvm]<br>fun [encryptData](encrypt-data.md)(plaintext: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cipher: [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html)): [CiphertextWrapper](../../../../domain/domain/com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getCiphertextWrapper](get-ciphertext-wrapper.md) | [androidJvm]<br>fun [getCiphertextWrapper](get-ciphertext-wrapper.md)(): [CiphertextWrapper](../../../../domain/domain/com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getInitializedCipherForDecryption](get-initialized-cipher-for-decryption.md) | [androidJvm]<br>fun [getInitializedCipherForDecryption](get-initialized-cipher-for-decryption.md)(keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), initializationVector: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)): [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html) |
| [getInitializedCipherForEncryption](get-initialized-cipher-for-encryption.md) | [androidJvm]<br>fun [getInitializedCipherForEncryption](get-initialized-cipher-for-encryption.md)(keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Cipher](https://developer.android.com/reference/kotlin/javax/crypto/Cipher.html) |
| [setCiphertextWrapper](set-ciphertext-wrapper.md) | [androidJvm]<br>fun [setCiphertextWrapper](set-ciphertext-wrapper.md)(ciphertextWrapper: [CiphertextWrapper](../../../../domain/domain/com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md)) |
