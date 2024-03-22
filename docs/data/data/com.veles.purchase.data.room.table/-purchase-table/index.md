//[data](../../../index.md)/[com.veles.purchase.data.room.table](../index.md)/[PurchaseTable](index.md)

# PurchaseTable

[androidJvm]\
class [PurchaseTable](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val typeHistory: [HistoryType](../../../../domain/domain/com.veles.purchase.domain.model.history/-history-type/index.md), val time: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), val collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString())

## Constructors

| | |
|---|---|
| [PurchaseTable](-purchase-table.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = createPrimaryIDKey(), text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), count: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), check: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), typeHistory: [HistoryType](../../../../domain/domain/com.veles.purchase.domain.model.history/-history-type/index.md), time: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString(), collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = emptyString()) |

## Properties

| Name | Summary |
|---|---|
| [check](check.md) | [androidJvm]<br>val [check](check.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [collectionId](collection-id.md) | [androidJvm]<br>val [collectionId](collection-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [count](count.md) | [androidJvm]<br>val [count](count.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [price](price.md) | [androidJvm]<br>val [price](price.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [text](text.md) | [androidJvm]<br>val [text](text.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [time](time.md) | [androidJvm]<br>val [time](time.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [typeHistory](type-history.md) | [androidJvm]<br>val [typeHistory](type-history.md): [HistoryType](../../../../domain/domain/com.veles.purchase.domain.model.history/-history-type/index.md) |

## Functions

| Name | Summary |
|---|---|
| [toPurchaseTableModel](../to-purchase-table-model.md) | [androidJvm]<br>fun [PurchaseTable](index.md).[toPurchaseTableModel](../to-purchase-table-model.md)(): [PurchaseTableModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-table-model/index.md) |
