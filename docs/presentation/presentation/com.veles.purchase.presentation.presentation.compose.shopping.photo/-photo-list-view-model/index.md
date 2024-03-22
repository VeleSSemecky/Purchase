//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.compose.shopping.photo](../index.md)/[PhotoListViewModel](index.md)

# PhotoListViewModel

[androidJvm]\
class [PhotoListViewModel](index.md)@Injectconstructor(deleteSkuPhotoUseCase: [DeleteSkuPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-delete-sku-photo-use-case/index.md), args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [PhotoListViewModel](-photo-list-view-model.md) | [androidJvm]<br>@Inject<br>constructor(deleteSkuPhotoUseCase: [DeleteSkuPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-delete-sku-photo-use-case/index.md), args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowSkuPhotoEntity](flow-sku-photo-entity.md) | [androidJvm]<br>val [flowSkuPhotoEntity](flow-sku-photo-entity.md): MutableStateFlow&lt;[SkuPhotoEntity](../../../../data/data/com.veles.purchase.data.room.table/-sku-photo-entity/index.md)?&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [deletePhoto](delete-photo.md) | [androidJvm]<br>fun [deletePhoto](delete-photo.md)(onBack: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |
