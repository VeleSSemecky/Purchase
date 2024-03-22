//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list](../index.md)/[CollectionPurchaseComposeViewModel](index.md)

# CollectionPurchaseComposeViewModel

[androidJvm]\
class [CollectionPurchaseComposeViewModel](index.md)@Injectconstructor(firebaseFirestorePurchaseCollectionUseCase: [FirebaseFirestorePurchaseCollectionUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-firebase-firestore-purchase-collection-use-case/index.md), deletePurchaseCollectionUseCase: [DeletePurchaseCollectionUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-delete-purchase-collection-use-case/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [CollectionPurchaseComposeViewModel](-collection-purchase-compose-view-model.md) | [androidJvm]<br>@Inject<br>constructor(firebaseFirestorePurchaseCollectionUseCase: [FirebaseFirestorePurchaseCollectionUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-firebase-firestore-purchase-collection-use-case/index.md), deletePurchaseCollectionUseCase: [DeletePurchaseCollectionUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-delete-purchase-collection-use-case/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [stateFlowDeletePurchaseCollections](state-flow-delete-purchase-collections.md) | [androidJvm]<br>val [stateFlowDeletePurchaseCollections](state-flow-delete-purchase-collections.md): MutableStateFlow&lt;[PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)?&gt; |
| [stateFlowListPurchaseCollections](state-flow-list-purchase-collections.md) | [androidJvm]<br>val [stateFlowListPurchaseCollections](state-flow-list-purchase-collections.md): MutableStateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)&gt;&gt; |
| [stateFlowProgress](state-flow-progress.md) | [androidJvm]<br>val [stateFlowProgress](state-flow-progress.md): MutableStateFlow&lt;[Progress](../../com.veles.purchase.presentation.model.progress/-progress/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [apiFirebaseRemovePurchaseCollection](api-firebase-remove-purchase-collection.md) | [androidJvm]<br>fun [apiFirebaseRemovePurchaseCollection](api-firebase-remove-purchase-collection.md)(item: [PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)): Job |
| [onDeletePurchaseCollections](on-delete-purchase-collections.md) | [androidJvm]<br>fun [onDeletePurchaseCollections](on-delete-purchase-collections.md)(purchaseCollection: [PurchaseCollectionModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-collection-model/index.md)?): Job |
