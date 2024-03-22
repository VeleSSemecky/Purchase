//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.biometric](../index.md)/[DecryptionUseCase](index.md)

# DecryptionUseCase

[jvm]\
class [DecryptionUseCase](index.md)@Injectconstructor(biometricRepository: [BiometricRepository](../../com.veles.purchase.domain.repository.auth/-biometric-repository/index.md))

## Constructors

| | |
|---|---|
| [DecryptionUseCase](-decryption-use-case.md) | [jvm]<br>@Inject<br>constructor(biometricRepository: [BiometricRepository](../../com.veles.purchase.domain.repository.auth/-biometric-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [decryptData](decrypt-data.md) | [jvm]<br>fun [decryptData](decrypt-data.md)(cipher: [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getCiphertextWrapper](get-ciphertext-wrapper.md) | [jvm]<br>fun [getCiphertextWrapper](get-ciphertext-wrapper.md)(): [CiphertextWrapper](../../com.veles.purchase.domain.model.cryptography/-ciphertext-wrapper/index.md) |
| [getCryptoObjectForDecryption](get-crypto-object-for-decryption.md) | [jvm]<br>fun [getCryptoObjectForDecryption](get-crypto-object-for-decryption.md)(initializationVector: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)): [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)? |
