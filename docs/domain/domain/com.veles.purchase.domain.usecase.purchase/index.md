//[domain](../../index.md)/[com.veles.purchase.domain.usecase.purchase](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AddLazyPurchaseUseCase](-add-lazy-purchase-use-case/index.md) | [jvm]<br>class [AddLazyPurchaseUseCase](-add-lazy-purchase-use-case/index.md)@Injectconstructor(appCoroutineDispatcher: [AppCoroutineDispatcher](../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseRepository: [SetPurchaseRepository](../com.veles.purchase.domain.repository.purchase/-set-purchase-repository/index.md), notificationMessageRepository: [NotificationMessageRepository](../com.veles.purchase.domain.repository.message/-notification-message-repository/index.md), historyRepository: [HistoryRepository](../com.veles.purchase.domain.repository.history/-history-repository/index.md)) |
| [CheckPurchaseUseCase](-check-purchase-use-case/index.md) | [jvm]<br>class [CheckPurchaseUseCase](-check-purchase-use-case/index.md)@Injectconstructor(coroutineDispatcher: [AppCoroutineDispatcher](../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseHistoryUseCase: [SetPurchaseHistoryUseCase](-set-purchase-history-use-case/index.md), firebasePurchaseSendUseCase: [FirebasePurchaseSendUseCase](-firebase-purchase-send-use-case/index.md)) |
| [DeletePurchaseUseCase](-delete-purchase-use-case/index.md) | [jvm]<br>class [DeletePurchaseUseCase](-delete-purchase-use-case/index.md)@Injectconstructor(deletePurchaseRepository: [DeletePurchaseRepository](../com.veles.purchase.domain.repository.storage/-delete-purchase-repository/index.md), deletePurchasePhotoRepository: [DeletePurchasePhotoRepository](../com.veles.purchase.domain.repository.storage/-delete-purchase-photo-repository/index.md), historyRepository: [HistoryRepository](../com.veles.purchase.domain.repository.history/-history-repository/index.md)) |
| [FirebasePurchaseGetHistoryUseCase](-firebase-purchase-get-history-use-case/index.md) | [jvm]<br>class [FirebasePurchaseGetHistoryUseCase](-firebase-purchase-get-history-use-case/index.md)@Injectconstructor(historyRepository: [HistoryRepository](../com.veles.purchase.domain.repository.history/-history-repository/index.md)) |
| [FirebasePurchaseSendUseCase](-firebase-purchase-send-use-case/index.md) | [jvm]<br>class [FirebasePurchaseSendUseCase](-firebase-purchase-send-use-case/index.md)@Injectconstructor(setPurchaseRepository: [SetPurchaseRepository](../com.veles.purchase.domain.repository.purchase/-set-purchase-repository/index.md)) |
| [GetPurchasesUseCase](-get-purchases-use-case/index.md) | [jvm]<br>class [GetPurchasesUseCase](-get-purchases-use-case/index.md)@Injectconstructor(getPurchaseRepository: [GetPurchaseRepository](../com.veles.purchase.domain.repository.purchase/-get-purchase-repository/index.md)) |
| [GetPurchaseUseCase](-get-purchase-use-case/index.md) | [jvm]<br>class [GetPurchaseUseCase](-get-purchase-use-case/index.md)@Injectconstructor(getPurchaseRepository: [GetPurchaseRepository](../com.veles.purchase.domain.repository.purchase/-get-purchase-repository/index.md)) |
| [MoveForLaterPurchaseUseCase](-move-for-later-purchase-use-case/index.md) | [jvm]<br>class [MoveForLaterPurchaseUseCase](-move-for-later-purchase-use-case/index.md)@Injectconstructor(purchaseLaterRepository: [PurchaseLaterRepository](../com.veles.purchase.domain.repository.purchase/-purchase-later-repository/index.md), deletePurchaseRepository: [DeletePurchaseRepository](../com.veles.purchase.domain.repository.storage/-delete-purchase-repository/index.md)) |
| [SavePurchaseUseCase](-save-purchase-use-case/index.md) | [jvm]<br>class [SavePurchaseUseCase](-save-purchase-use-case/index.md)@Injectconstructor(coroutineDispatcher: [AppCoroutineDispatcher](../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseHistoryUseCase: [SetPurchaseHistoryUseCase](-set-purchase-history-use-case/index.md), storageDeleteUseCase: [StorageDeleteUseCase](../com.veles.purchase.domain.usecase.storage/-storage-delete-use-case/index.md), firebaseStorageUseCase: [FirebaseStorageUseCase](../com.veles.purchase.domain.usecase.storage/-firebase-storage-use-case/index.md), firebasePurchaseSendUseCase: [FirebasePurchaseSendUseCase](-firebase-purchase-send-use-case/index.md), notificationMessageUseCase: [NotificationMessageUseCase](../com.veles.purchase.domain.usecase/-notification-message-use-case/index.md), priceUseCase: [PriceUseCase](../com.veles.purchase.domain.usecase.price/-price-use-case/index.md)) |
| [SetPurchaseHistoryUseCase](-set-purchase-history-use-case/index.md) | [jvm]<br>class [SetPurchaseHistoryUseCase](-set-purchase-history-use-case/index.md)@Injectconstructor(historyRepository: [HistoryRepository](../com.veles.purchase.domain.repository.history/-history-repository/index.md)) |