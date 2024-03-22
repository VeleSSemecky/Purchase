//[data](../../../index.md)/[com.veles.purchase.data.local.data](../index.md)/[DataStoreImpl](index.md)

# DataStoreImpl

[androidJvm]\
@Singleton

class [DataStoreImpl](index.md)@Injectconstructor(sharedPreferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) : [DataStore](../-data-store/index.md)

## Constructors

| | |
|---|---|
| [DataStoreImpl](-data-store-impl.md) | [androidJvm]<br>@Inject<br>constructor(sharedPreferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [clear](clear.md) | [androidJvm]<br>open override fun [clear](clear.md)() |
| [getAccessToken](get-access-token.md) | [androidJvm]<br>open override fun [getAccessToken](get-access-token.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getCityId](get-city-id.md) | [androidJvm]<br>open override fun [getCityId](get-city-id.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getCityName](get-city-name.md) | [androidJvm]<br>open override fun [getCityName](get-city-name.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getFCMToken](get-f-c-m-token.md) | [androidJvm]<br>open override fun [getFCMToken](get-f-c-m-token.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getIV](get-i-v.md) | [androidJvm]<br>open override fun [getIV](get-i-v.md)(): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [getPinEncrypt](get-pin-encrypt.md) | [androidJvm]<br>open override fun [getPinEncrypt](get-pin-encrypt.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getRefreshToken](get-refresh-token.md) | [androidJvm]<br>open override fun [getRefreshToken](get-refresh-token.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isInForeground](is-in-foreground.md) | [androidJvm]<br>open override fun [isInForeground](is-in-foreground.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isUseFingerprint](is-use-fingerprint.md) | [androidJvm]<br>open override fun [isUseFingerprint](is-use-fingerprint.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setAccessToken](set-access-token.md) | [androidJvm]<br>open override fun [setAccessToken](set-access-token.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setCityId](set-city-id.md) | [androidJvm]<br>open override fun [setCityId](set-city-id.md)(cityId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setCityName](set-city-name.md) | [androidJvm]<br>open override fun [setCityName](set-city-name.md)(city: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setFCMToken](set-f-c-m-token.md) | [androidJvm]<br>open override fun [setFCMToken](set-f-c-m-token.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setIsInForeground](set-is-in-foreground.md) | [androidJvm]<br>open override fun [setIsInForeground](set-is-in-foreground.md)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setIsUseFingerprint](set-is-use-fingerprint.md) | [androidJvm]<br>open override fun [setIsUseFingerprint](set-is-use-fingerprint.md)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setIV](set-i-v.md) | [androidJvm]<br>open override fun [setIV](set-i-v.md)(encryptionIv: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)) |
| [setPinEncrypt](set-pin-encrypt.md) | [androidJvm]<br>open override fun [setPinEncrypt](set-pin-encrypt.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setRefreshToken](set-refresh-token.md) | [androidJvm]<br>open override fun [setRefreshToken](set-refresh-token.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
