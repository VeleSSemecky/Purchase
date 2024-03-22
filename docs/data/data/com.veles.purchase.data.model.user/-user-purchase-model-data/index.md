//[data](../../../index.md)/[com.veles.purchase.data.model.user](../index.md)/[UserPurchaseModelData](index.md)

# UserPurchaseModelData

[androidJvm]\
data class [UserPurchaseModelData](index.md)(val uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), val phoneNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), val fcmToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = emptyString(), val photoUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = emptyString()) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [UserPurchaseModelData](-user-purchase-model-data.md) | [androidJvm]<br>constructor(uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), phoneNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = dashString(), fcmToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = emptyString(), photoUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = emptyString()) |

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
| [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toUserPurchaseModel](../to-user-purchase-model.md) | [androidJvm]<br>fun [UserPurchaseModelData](index.md).[toUserPurchaseModel](../to-user-purchase-model.md)(): [UserPurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.user/-user-purchase-model/index.md) |
| [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
