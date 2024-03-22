//[data](../../../index.md)/[com.veles.purchase.data.repository.collection.get](../index.md)/[GetCollectionPurchaseRepositoryImpl](index.md)

# GetCollectionPurchaseRepositoryImpl

[androidJvm]\
@Singleton

class [GetCollectionPurchaseRepositoryImpl](index.md)@Injectconstructor(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) : [GetCollectionPurchaseRepository](../../../../domain/domain/com.veles.purchase.domain.repository.collection/-get-collection-purchase-repository/index.md)

## Constructors

| | |
|---|---|
| [GetCollectionPurchaseRepositoryImpl](-get-collection-purchase-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) |

## Functions

| Name | Summary |
|---|---|
| [getCollectionPurchase](get-collection-purchase.md) | [androidJvm]<br>open suspend override fun [getCollectionPurchase](get-collection-purchase.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md) |
| [getListCollectionPurchases](get-list-collection-purchases.md) | [androidJvm]<br>open suspend override fun [getListCollectionPurchases](get-list-collection-purchases.md)(): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)&gt;&gt; |
