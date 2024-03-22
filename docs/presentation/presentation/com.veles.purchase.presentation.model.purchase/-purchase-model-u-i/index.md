//[presentation](../../../index.md)/[com.veles.purchase.presentation.model.purchase](../index.md)/[PurchaseModelUI](index.md)

# PurchaseModelUI

[androidJvm]\
data class [PurchaseModelUI](index.md)(val createId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Calendar.getInstance().timeInMillis.toString(), val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val userList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList(), val listImage: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModelUI](../-purchase-photo-model-u-i/index.md)&gt; = emptyList()) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [PurchaseModelUI](-purchase-model-u-i.md) | [androidJvm]<br>constructor(createId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Calendar.getInstance().timeInMillis.toString(), text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), userList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList(), listImage: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModelUI](../-purchase-photo-model-u-i/index.md)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [check](check.md) | [androidJvm]<br>val [check](check.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [count](count.md) | [androidJvm]<br>val [count](count.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [createId](create-id.md) | [androidJvm]<br>val [createId](create-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [listImage](list-image.md) | [androidJvm]<br>val [listImage](list-image.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModelUI](../-purchase-photo-model-u-i/index.md)&gt; |
| [price](price.md) | [androidJvm]<br>val [price](price.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [text](text.md) | [androidJvm]<br>val [text](text.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [userList](user-list.md) | [androidJvm]<br>val [userList](user-list.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1578325224%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1578325224%2FFunctions%2F-646359276)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toPurchaseModel](../to-purchase-model.md) | [androidJvm]<br>fun [PurchaseModelUI](index.md).[toPurchaseModel](../to-purchase-model.md)(): [PurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-model/index.md) |
| [writeToParcel](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1754457655%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1754457655%2FFunctions%2F-646359276)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
