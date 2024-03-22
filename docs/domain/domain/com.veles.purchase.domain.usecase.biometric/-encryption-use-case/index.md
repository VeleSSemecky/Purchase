//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.biometric](../index.md)/[EncryptionUseCase](index.md)

# EncryptionUseCase

[jvm]\
class [EncryptionUseCase](index.md)@Injectconstructor(biometricRepository: [BiometricRepository](../../com.veles.purchase.domain.repository.auth/-biometric-repository/index.md))

## Constructors

| | |
|---|---|
| [EncryptionUseCase](-encryption-use-case.md) | [jvm]<br>@Inject<br>constructor(biometricRepository: [BiometricRepository](../../com.veles.purchase.domain.repository.auth/-biometric-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [encryptAndSaveData](encrypt-and-save-data.md) | [jvm]<br>fun [encryptAndSaveData](encrypt-and-save-data.md)(plaintext: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cipher: [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)?) |
| [getCryptoObjectForEncryption](get-crypto-object-for-encryption.md) | [jvm]<br>fun [getCryptoObjectForEncryption](get-crypto-object-for-encryption.md)(): [Cipher](https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html)? |
