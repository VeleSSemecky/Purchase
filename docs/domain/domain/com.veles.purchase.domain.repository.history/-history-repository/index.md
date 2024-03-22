//[domain](../../../index.md)/[com.veles.purchase.domain.repository.history](../index.md)/[HistoryRepository](index.md)

# HistoryRepository

[jvm]\
interface [HistoryRepository](index.md)

## Functions

| Name | Summary |
|---|---|
| [getHistory](get-history.md) | [jvm]<br>abstract suspend fun [getHistory](get-history.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseTableModel](../../com.veles.purchase.domain.model.purchase/-purchase-table-model/index.md)&gt; |
| [insert](insert.md) | [jvm]<br>abstract suspend fun [insert](insert.md)(purchaseTable: [PurchaseTableModel](../../com.veles.purchase.domain.model.purchase/-purchase-table-model/index.md)) |
