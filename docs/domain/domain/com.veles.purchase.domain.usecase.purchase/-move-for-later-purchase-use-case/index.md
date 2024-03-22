//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.purchase](../index.md)/[MoveForLaterPurchaseUseCase](index.md)

# MoveForLaterPurchaseUseCase

[jvm]\
class [MoveForLaterPurchaseUseCase](index.md)@Injectconstructor(purchaseLaterRepository: [PurchaseLaterRepository](../../com.veles.purchase.domain.repository.purchase/-purchase-later-repository/index.md), deletePurchaseRepository: [DeletePurchaseRepository](../../com.veles.purchase.domain.repository.storage/-delete-purchase-repository/index.md))

## Constructors

| | |
|---|---|
| [MoveForLaterPurchaseUseCase](-move-for-later-purchase-use-case.md) | [jvm]<br>@Inject<br>constructor(purchaseLaterRepository: [PurchaseLaterRepository](../../com.veles.purchase.domain.repository.purchase/-purchase-later-repository/index.md), deletePurchaseRepository: [DeletePurchaseRepository](../../com.veles.purchase.domain.repository.storage/-delete-purchase-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(purchaseModel: [PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md), purchaseCollectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
