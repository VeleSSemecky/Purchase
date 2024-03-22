//[domain](../../../index.md)/[com.veles.purchase.domain.model.purchase](../index.md)/[PurchaseModel](index.md)

# PurchaseModel

[jvm]\
data class [PurchaseModel](index.md)(val createId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val userList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val listImage: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../-purchase-photo-model/index.md)&gt;)

## Constructors

| | |
|---|---|
| [PurchaseModel](-purchase-model.md) | [jvm]<br>constructor(createId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, listImage: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../-purchase-photo-model/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [check](check.md) | [jvm]<br>val [check](check.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [count](count.md) | [jvm]<br>val [count](count.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [createId](create-id.md) | [jvm]<br>val [createId](create-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [listImage](list-image.md) | [jvm]<br>val [listImage](list-image.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../-purchase-photo-model/index.md)&gt; |
| [price](price.md) | [jvm]<br>val [price](price.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [text](text.md) | [jvm]<br>val [text](text.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [userList](user-list.md) | [jvm]<br>val [userList](user-list.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [createPurchaseTable](../create-purchase-table.md) | [jvm]<br>fun [PurchaseModel](index.md).[createPurchaseTable](../create-purchase-table.md)(typeHistory: [HistoryType](../../com.veles.purchase.domain.model.history/-history-type/index.md)): [PurchaseTableModel](../-purchase-table-model/index.md) |
| [dropImage](drop-image.md) | [jvm]<br>fun [dropImage](drop-image.md)(purchasePhotoModel: [PurchasePhotoModel](../-purchase-photo-model/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../-purchase-photo-model/index.md)&gt; |
| [getLocalListPurchasePhotoModel](get-local-list-purchase-photo-model.md) | [jvm]<br>fun [getLocalListPurchasePhotoModel](get-local-list-purchase-photo-model.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../-purchase-photo-model/index.md)&gt; |
| [isEmpty](is-empty.md) | [jvm]<br>fun [isEmpty](is-empty.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [toHistoryType](../../com.veles.purchase.domain.model.history/to-history-type.md) | [jvm]<br>fun [PurchaseModel](index.md).[toHistoryType](../../com.veles.purchase.domain.model.history/to-history-type.md)(): [HistoryType](../../com.veles.purchase.domain.model.history/-history-type/index.md) |
