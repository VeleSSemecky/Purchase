//[domain](../../../index.md)/[com.veles.purchase.domain.repository.purchase](../index.md)/[GetPurchaseRepository](index.md)

# GetPurchaseRepository

[jvm]\
interface [GetPurchaseRepository](index.md)

## Functions

| Name | Summary |
|---|---|
| [getLisPurchases](get-lis-purchases.md) | [jvm]<br>abstract fun [getLisPurchases](get-lis-purchases.md)(collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md)&gt;&gt; |
| [getPurchase](get-purchase.md) | [jvm]<br>abstract suspend fun [getPurchase](get-purchase.md)(collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), purchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md) |
