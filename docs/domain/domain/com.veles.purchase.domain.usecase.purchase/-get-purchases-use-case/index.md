//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.purchase](../index.md)/[GetPurchasesUseCase](index.md)

# GetPurchasesUseCase

[jvm]\
class [GetPurchasesUseCase](index.md)@Injectconstructor(getPurchaseRepository: [GetPurchaseRepository](../../com.veles.purchase.domain.repository.purchase/-get-purchase-repository/index.md))

## Constructors

| | |
|---|---|
| [GetPurchasesUseCase](-get-purchases-use-case.md) | [jvm]<br>@Inject<br>constructor(getPurchaseRepository: [GetPurchaseRepository](../../com.veles.purchase.domain.repository.purchase/-get-purchase-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>operator fun [invoke](invoke.md)(modelCollectionPurchaseId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md)&gt;&gt; |
