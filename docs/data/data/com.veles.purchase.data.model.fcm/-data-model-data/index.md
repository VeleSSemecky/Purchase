//[data](../../../index.md)/[com.veles.purchase.data.model.fcm](../index.md)/[DataModelData](index.md)

# DataModelData

[androidJvm]\
data class [DataModelData](index.md)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val tokenSender: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [DataModelData](-data-model-data.md) | [androidJvm]<br>constructor(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tokenSender: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [message](message.md) | [androidJvm]<br>val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [title](title.md) | [androidJvm]<br>val [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [tokenSender](token-sender.md) | [androidJvm]<br>val [tokenSender](token-sender.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toDataModel](../to-data-model.md) | [androidJvm]<br>fun [DataModelData](index.md).[toDataModel](../to-data-model.md)(): [DataModel](../../../../domain/domain/com.veles.purchase.domain.model.fcm/-data-model/index.md) |
| [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
