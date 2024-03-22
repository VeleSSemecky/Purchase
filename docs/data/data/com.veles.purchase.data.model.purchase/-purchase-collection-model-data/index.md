//[data](../../../index.md)/[com.veles.purchase.data.model.purchase](../index.md)/[PurchaseCollectionModelData](index.md)

# PurchaseCollectionModelData

[androidJvm]\
data class [PurchaseCollectionModelData](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Calendar.getInstance().timeInMillis.toString(), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val creator: [UserPurchaseModelData](../../com.veles.purchase.data.model.user/-user-purchase-model-data/index.md) = UserPurchaseModelData(), val listMembers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf()) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

## Constructors

| | |
|---|---|
| [PurchaseCollectionModelData](-purchase-collection-model-data.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Calendar.getInstance().timeInMillis.toString(), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), creator: [UserPurchaseModelData](../../com.veles.purchase.data.model.user/-user-purchase-model-data/index.md) = UserPurchaseModelData(), listMembers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = arrayListOf()) |

## Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | [androidJvm]<br>val [creator](creator.md): [UserPurchaseModelData](../../com.veles.purchase.data.model.user/-user-purchase-model-data/index.md) |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [listMembers](list-members.md) | [androidJvm]<br>val [listMembers](list-members.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [name](name.md) | [androidJvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [describeContents](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1578325224%2FFunctions%2F-70787932)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toPurchaseCollectionModel](../to-purchase-collection-model.md) | [androidJvm]<br>fun [PurchaseCollectionModelData](index.md).[toPurchaseCollectionModel](../to-purchase-collection-model.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md) |
| [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932) | [androidJvm]<br>abstract fun [writeToParcel](../../com.veles.purchase.data.room.table/-sku-photo-entity/index.md#-1754457655%2FFunctions%2F-70787932)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
