//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.purchase](../index.md)/[CheckPurchaseUseCase](index.md)

# CheckPurchaseUseCase

[jvm]\
class [CheckPurchaseUseCase](index.md)@Injectconstructor(coroutineDispatcher: [AppCoroutineDispatcher](../../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseHistoryUseCase: [SetPurchaseHistoryUseCase](../-set-purchase-history-use-case/index.md), firebasePurchaseSendUseCase: [FirebasePurchaseSendUseCase](../-firebase-purchase-send-use-case/index.md))

## Constructors

| | |
|---|---|
| [CheckPurchaseUseCase](-check-purchase-use-case.md) | [jvm]<br>@Inject<br>constructor(coroutineDispatcher: [AppCoroutineDispatcher](../../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseHistoryUseCase: [SetPurchaseHistoryUseCase](../-set-purchase-history-use-case/index.md), firebasePurchaseSendUseCase: [FirebasePurchaseSendUseCase](../-firebase-purchase-send-use-case/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(purchaseCollectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), purchaseModel: [PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md)) |
