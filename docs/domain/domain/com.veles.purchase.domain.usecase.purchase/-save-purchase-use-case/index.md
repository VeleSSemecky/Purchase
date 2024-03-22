//[domain](../../../index.md)/[com.veles.purchase.domain.usecase.purchase](../index.md)/[SavePurchaseUseCase](index.md)

# SavePurchaseUseCase

[jvm]\
class [SavePurchaseUseCase](index.md)@Injectconstructor(coroutineDispatcher: [AppCoroutineDispatcher](../../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseHistoryUseCase: [SetPurchaseHistoryUseCase](../-set-purchase-history-use-case/index.md), storageDeleteUseCase: [StorageDeleteUseCase](../../com.veles.purchase.domain.usecase.storage/-storage-delete-use-case/index.md), firebaseStorageUseCase: [FirebaseStorageUseCase](../../com.veles.purchase.domain.usecase.storage/-firebase-storage-use-case/index.md), firebasePurchaseSendUseCase: [FirebasePurchaseSendUseCase](../-firebase-purchase-send-use-case/index.md), notificationMessageUseCase: [NotificationMessageUseCase](../../com.veles.purchase.domain.usecase/-notification-message-use-case/index.md), priceUseCase: [PriceUseCase](../../com.veles.purchase.domain.usecase.price/-price-use-case/index.md))

## Constructors

| | |
|---|---|
| [SavePurchaseUseCase](-save-purchase-use-case.md) | [jvm]<br>@Inject<br>constructor(coroutineDispatcher: [AppCoroutineDispatcher](../../com.veles.purchase.domain.core.dispatcher/-app-coroutine-dispatcher/index.md), setPurchaseHistoryUseCase: [SetPurchaseHistoryUseCase](../-set-purchase-history-use-case/index.md), storageDeleteUseCase: [StorageDeleteUseCase](../../com.veles.purchase.domain.usecase.storage/-storage-delete-use-case/index.md), firebaseStorageUseCase: [FirebaseStorageUseCase](../../com.veles.purchase.domain.usecase.storage/-firebase-storage-use-case/index.md), firebasePurchaseSendUseCase: [FirebasePurchaseSendUseCase](../-firebase-purchase-send-use-case/index.md), notificationMessageUseCase: [NotificationMessageUseCase](../../com.veles.purchase.domain.usecase/-notification-message-use-case/index.md), priceUseCase: [PriceUseCase](../../com.veles.purchase.domain.usecase.price/-price-use-case/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [jvm]<br>suspend operator fun [invoke](invoke.md)(purchaseCollectionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), listPurchasePhotoModel: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../../com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)&gt;, editPurchaseModel: [PurchaseModel](../../com.veles.purchase.domain.model.purchase/-purchase-model/index.md), isNewPurchase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
