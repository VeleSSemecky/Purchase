//[data](../../../index.md)/[com.veles.purchase.data.room.dao](../index.md)/[PurchaseDAO](index.md)

# PurchaseDAO

[androidJvm]\
interface [PurchaseDAO](index.md)

## Functions

| Name | Summary |
|---|---|
| [delete](delete.md) | [androidJvm]<br>abstract suspend fun [delete](delete.md)(purchaseTable: [PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>abstract suspend fun [delete](delete.md)(purchaseTables: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md)&gt;) |
| [getListPurchaseTables](get-list-purchase-tables.md) | [androidJvm]<br>abstract suspend fun [getListPurchaseTables](get-list-purchase-tables.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md)&gt; |
| [insert](insert.md) | [androidJvm]<br>abstract suspend fun [insert](insert.md)(purchaseTable: [PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [insertAll](insert-all.md) | [androidJvm]<br>abstract suspend fun [insertAll](insert-all.md)(vararg purchaseTables: [PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md))<br>abstract suspend fun [insertAll](insert-all.md)(purchaseTables: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md)&gt;) |
| [update](update.md) | [androidJvm]<br>abstract suspend fun [update](update.md)(purchaseTable: [PurchaseTable](../../com.veles.purchase.data.room.table/-purchase-table/index.md)) |
