//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.compose.shopping.list](../index.md)/[SkuListViewModel](index.md)

# SkuListViewModel

[androidJvm]\
class [SkuListViewModel](index.md)@Injectconstructor(deleteSkuUseCase: [DeleteSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-delete-sku-use-case/index.md), getSkuUseCase: [GetSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-use-case/index.md), sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [SkuListViewModel](-sku-list-view-model.md) | [androidJvm]<br>@Inject<br>constructor(deleteSkuUseCase: [DeleteSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-delete-sku-use-case/index.md), getSkuUseCase: [GetSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-use-case/index.md), sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowSkuEntityList](flow-sku-entity-list.md) | [androidJvm]<br>val [flowSkuEntityList](flow-sku-entity-list.md): MutableStateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-model/index.md)&gt;&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [deleteSkuEntity](delete-sku-entity.md) | [androidJvm]<br>fun [deleteSkuEntity](delete-sku-entity.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [initList](init-list.md) | [androidJvm]<br>fun [initList](init-list.md)(): Job |
