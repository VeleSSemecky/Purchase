//[data](../../../index.md)/[com.veles.purchase.data.repository.history](../index.md)/[HistoryRepositoryImpl](index.md)

# HistoryRepositoryImpl

[androidJvm]\
@Singleton

class [HistoryRepositoryImpl](index.md)@Injectconstructor(purchaseDAO: [PurchaseDAO](../../com.veles.purchase.data.room.dao/-purchase-d-a-o/index.md)) : [HistoryRepository](../../../../domain/domain/com.veles.purchase.domain.repository.history/-history-repository/index.md)

## Constructors

| | |
|---|---|
| [HistoryRepositoryImpl](-history-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(purchaseDAO: [PurchaseDAO](../../com.veles.purchase.data.room.dao/-purchase-d-a-o/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getHistory](get-history.md) | [androidJvm]<br>open suspend override fun [getHistory](get-history.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseTableModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-table-model/index.md)&gt; |
| [insert](insert.md) | [androidJvm]<br>open suspend override fun [insert](insert.md)(purchaseTable: [PurchaseTableModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-table-model/index.md)) |
