//[presentation](../../../index.md)/[com.veles.purchase.presentation.model.purchase](../index.md)/[PurchaseCollectionModelUI](index.md)

# PurchaseCollectionModelUI

[androidJvm]\
data class [PurchaseCollectionModelUI](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Calendar.getInstance().timeInMillis.toString(), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val creator: [UserPurchaseModelUI](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md) = UserPurchaseModelUI(), val listMembers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf(), val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [PurchaseCollectionModelUI](-purchase-collection-model-u-i.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Calendar.getInstance().timeInMillis.toString(), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), creator: [UserPurchaseModelUI](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md) = UserPurchaseModelUI(), listMembers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf(), count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [androidJvm]<br>val [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [creator](creator.md) | [androidJvm]<br>val [creator](creator.md): [UserPurchaseModelUI](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md) |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [listMembers](list-members.md) | [androidJvm]<br>val [listMembers](list-members.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [name](name.md) | [androidJvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1578325224%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1578325224%2FFunctions%2F-646359276)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toPurchaseCollectionModel](../to-purchase-collection-model.md) | [androidJvm]<br>fun [PurchaseCollectionModelUI](index.md).[toPurchaseCollectionModel](../to-purchase-collection-model.md)(): [PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md) |
| [writeToParcel](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1754457655%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.presentation.model.user/-user-purchase-model-u-i/index.md#-1754457655%2FFunctions%2F-646359276)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
