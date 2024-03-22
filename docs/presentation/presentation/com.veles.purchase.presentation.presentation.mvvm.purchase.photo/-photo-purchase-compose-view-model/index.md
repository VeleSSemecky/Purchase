//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.photo](../index.md)/[PhotoPurchaseComposeViewModel](index.md)

# PhotoPurchaseComposeViewModel

[androidJvm]\
class [PhotoPurchaseComposeViewModel](index.md)@Injectconstructor(args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md), sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), getPhotoUseCase: [GetPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.storage/-get-photo-use-case/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [PhotoPurchaseComposeViewModel](-photo-purchase-compose-view-model.md) | [androidJvm]<br>@Inject<br>constructor(args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md), sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), getPhotoUseCase: [GetPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.storage/-get-photo-use-case/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowProgress](flow-progress.md) | [androidJvm]<br>val [flowProgress](flow-progress.md): MutableStateFlow&lt;[Progress](../../com.veles.purchase.presentation.model.progress/-progress/index.md)&gt; |
| [flowPurchasePhotoModel](flow-purchase-photo-model.md) | [androidJvm]<br>val [flowPurchasePhotoModel](flow-purchase-photo-model.md): MutableStateFlow&lt;[PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)?&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [apiDatabaseURL](api-database-u-r-l.md) | [androidJvm]<br>fun [apiDatabaseURL](api-database-u-r-l.md)(purchasePhotoModel: [PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [deletePhoto](delete-photo.md) | [androidJvm]<br>fun [deletePhoto](delete-photo.md)(): Job |
| [onBackClicked](on-back-clicked.md) | [androidJvm]<br>fun [onBackClicked](on-back-clicked.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
