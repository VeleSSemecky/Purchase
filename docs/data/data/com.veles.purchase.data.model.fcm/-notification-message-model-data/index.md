//[data](../../../index.md)/[com.veles.purchase.data.model.fcm](../index.md)/[NotificationMessageModelData](index.md)

# NotificationMessageModelData

[androidJvm]\
data class [NotificationMessageModelData](index.md)(val registrationIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf(), val data: [DataModelData](../-data-model-data/index.md)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [NotificationMessageModelData](-notification-message-model-data.md) | [androidJvm]<br>constructor(registrationIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf(), data: [DataModelData](../-data-model-data/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [data](data.md) | [androidJvm]<br>@SerializedName(value = &quot;data&quot;)<br>val [data](data.md): [DataModelData](../-data-model-data/index.md) |
| [registrationIds](registration-ids.md) | [androidJvm]<br>@SerializedName(value = &quot;registration_ids&quot;)<br>val [registrationIds](registration-ids.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toNotificationMessageModel](../to-notification-message-model.md) | [androidJvm]<br>fun [NotificationMessageModelData](index.md).[toNotificationMessageModel](../to-notification-message-model.md)(): [NotificationMessageModel](../../../../domain/domain/com.veles.purchase.domain.model.fcm/-notification-message-model/index.md) |
| [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
