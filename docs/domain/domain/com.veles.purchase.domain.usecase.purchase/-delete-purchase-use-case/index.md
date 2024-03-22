//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.purchase](../index.md)/[DeletePurchaseUseCase](index.md)

# DeletePurchaseUseCase

[jvm]\
class [DeletePurchaseUseCase](index.md)@Injectconstructor(deletePurchaseRepository: [DeletePurchaseRepository](../../com.veles.purchase.domain.repository.storage/-delete-purchase-repository/index.md), deletePurchasePhotoRepository: [DeletePurchasePhotoRepository](../../com.veles.purchase.domain.repository.storage/-delete-purchase-photo-repository/index.md), historyRepository: [HistoryRepository](../../com.veles.purchase.domain.repository.history/-history-repository/index.md))

## Constructors

| | |
|---|---|
| [DeletePurchaseUseCase](-delete-purchase-use-case.md) | [jvm]<br>@Inject<br>constructor(deletePurchaseRepository: [DeletePurchaseRepository](../../com.veles.purchase.domain.repository.storage/-delete-purchase-repository/index.md), deletePurchasePhotoRepository: [DeletePurchasePhotoRepository](../../com.veles.purchase.domain.repository.storage/-delete-purchase-photo-repository/index.md), historyRepository: [HistoryRepository](../../com.veles.purchase.domain.repository.history/-history-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(purchaseModel: [PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md), purchaseCollectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
