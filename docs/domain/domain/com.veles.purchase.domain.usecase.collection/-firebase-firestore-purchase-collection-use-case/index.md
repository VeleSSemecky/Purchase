//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.collection](../index.md)/[FirebaseFirestorePurchaseCollectionUseCase](index.md)

# FirebaseFirestorePurchaseCollectionUseCase

[jvm]\
class [FirebaseFirestorePurchaseCollectionUseCase](index.md)@Injectconstructor(getCollectionPurchaseRepository: [GetCollectionPurchaseRepository](../../com.veles.purchase.domain.repository.collection/-get-collection-purchase-repository/index.md))

## Constructors

| | |
|---|---|
| [FirebaseFirestorePurchaseCollectionUseCase](-firebase-firestore-purchase-collection-use-case.md) | [jvm]<br>@Inject<br>constructor(getCollectionPurchaseRepository: [GetCollectionPurchaseRepository](../../com.veles.purchase.domain.repository.collection/-get-collection-purchase-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseCollectionModel](../../com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)&gt;&gt;<br>suspend operator fun [invoke](invoke.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [PurchaseCollectionModel](../../com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)? |
