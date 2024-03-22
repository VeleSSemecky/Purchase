//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.purchase](../index.md)/[AddLazyPurchaseUseCase](index.md)

# AddLazyPurchaseUseCase

[jvm]\
class [AddLazyPurchaseUseCase](index.md)@Injectconstructor(appCoroutineDispatcher: [AppCoroutineDispatcher](../../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseRepository: [SetPurchaseRepository](../../com.veles.purchase.domain.repository.purchase/-set-purchase-repository/index.md), notificationMessageRepository: [NotificationMessageRepository](../../com.veles.purchase.domain.repository.message/-notification-message-repository/index.md), historyRepository: [HistoryRepository](../../com.veles.purchase.domain.repository.history/-history-repository/index.md))

## Constructors

| | |
|---|---|
| [AddLazyPurchaseUseCase](-add-lazy-purchase-use-case.md) | [jvm]<br>@Inject<br>constructor(appCoroutineDispatcher: [AppCoroutineDispatcher](../../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseRepository: [SetPurchaseRepository](../../com.veles.purchase.domain.repository.purchase/-set-purchase-repository/index.md), notificationMessageRepository: [NotificationMessageRepository](../../com.veles.purchase.domain.repository.message/-notification-message-repository/index.md), historyRepository: [HistoryRepository](../../com.veles.purchase.domain.repository.history/-history-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(purchaseModel: [PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md), purchaseCollectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
