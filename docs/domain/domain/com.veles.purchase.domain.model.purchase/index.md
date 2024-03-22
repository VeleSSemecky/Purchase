//[domain](../../index.md)/[com.veles.purchase.domain.model.purchase](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [PhotoStatus](-photo-status/index.md) | [jvm]<br>enum [PhotoStatus](-photo-status/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[PhotoStatus](-photo-status/index.md)&gt; |
| [PurchaseCollectionModel](-purchase-collection-model/index.md) | [jvm]<br>data class [PurchaseCollectionModel](-purchase-collection-model/index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val creator: [UserPurchaseModel](../com.veles.purchase.domain.model.user/-user-purchase-model/index.md), val listMembers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [PurchaseModel](-purchase-model/index.md) | [jvm]<br>data class [PurchaseModel](-purchase-model/index.md)(val createId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val userList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val listImage: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](-purchase-photo-model/index.md)&gt;) |
| [PurchasePhotoModel](-purchase-photo-model/index.md) | [jvm]<br>data class [PurchasePhotoModel](-purchase-photo-model/index.md)(val purchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val purchasePhotoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), val purchasePhotoUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val status: [PhotoStatus](-photo-status/index.md) = PhotoStatus.LOCAL) |
| [PurchaseTableModel](-purchase-table-model/index.md) | [jvm]<br>class [PurchaseTableModel](-purchase-table-model/index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val typeHistory: [HistoryType](../com.veles.purchase.domain.model.history/-history-type/index.md), val time: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString()) |
| [Type](-type/index.md) | [jvm]<br>enum [Type](-type/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createPurchaseTable](create-purchase-table.md) | [jvm]<br>fun [PurchaseModel](-purchase-model/index.md).[createPurchaseTable](create-purchase-table.md)(typeHistory: [HistoryType](../com.veles.purchase.domain.model.history/-history-type/index.md)): [PurchaseTableModel](-purchase-table-model/index.md) |
