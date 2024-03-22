//[presentation](../../../index.md)/[com.veles.purchase.presentation.model.user](../index.md)/[UserPurchaseModelUI](index.md)

# UserPurchaseModelUI

[androidJvm]\
data class [UserPurchaseModelUI](index.md)(val uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, val phoneNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, val fcmToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, val photoUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [UserPurchaseModelUI](-user-purchase-model-u-i.md) | [androidJvm]<br>constructor(uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, phoneNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;-&quot;, fcmToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, photoUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;) |

## Properties

| Name | Summary |
|---|---|
| [displayName](display-name.md) | [androidJvm]<br>val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [email](email.md) | [androidJvm]<br>val [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [fcmToken](fcm-token.md) | [androidJvm]<br>val [fcmToken](fcm-token.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [phoneNumber](phone-number.md) | [androidJvm]<br>val [phoneNumber](phone-number.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [photoUrl](photo-url.md) | [androidJvm]<br>val [photoUrl](photo-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [providerId](provider-id.md) | [androidJvm]<br>val [providerId](provider-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [uid](uid.md) | [androidJvm]<br>val [uid](uid.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](index.md#-1578325224%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [describeContents](index.md#-1578325224%2FFunctions%2F-646359276)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toUserPurchaseModel](../to-user-purchase-model.md) | [androidJvm]<br>fun [UserPurchaseModelUI](index.md).[toUserPurchaseModel](../to-user-purchase-model.md)(): [UserPurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md) |
| [writeToParcel](index.md#-1754457655%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [writeToParcel](index.md#-1754457655%2FFunctions%2F-646359276)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
