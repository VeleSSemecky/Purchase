//[data](../../../index.md)/[com.veles.purchase.data.repository.purchase.get](../index.md)/[GetGetPurchaseRepositoryImpl](index.md)

# GetGetPurchaseRepositoryImpl

[androidJvm]\
@Singleton

class [GetGetPurchaseRepositoryImpl](index.md)@Injectconstructor(firebaseFirestore: FirebaseFirestore) : [GetPurchaseRepository](../../../../domain/domain/com.veles.purchase.domain.repository.purchase/-get-purchase-repository/index.md)

## Constructors

| | |
|---|---|
| [GetGetPurchaseRepositoryImpl](-get-get-purchase-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(firebaseFirestore: FirebaseFirestore) |

## Functions

| Name | Summary |
|---|---|
| [getLisPurchases](get-lis-purchases.md) | [androidJvm]<br>open override fun [getLisPurchases](get-lis-purchases.md)(collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-model/index.md)&gt;&gt; |
| [getPurchase](get-purchase.md) | [androidJvm]<br>open suspend override fun [getPurchase](get-purchase.md)(collectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), purchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [PurchaseModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-model/index.md) |
