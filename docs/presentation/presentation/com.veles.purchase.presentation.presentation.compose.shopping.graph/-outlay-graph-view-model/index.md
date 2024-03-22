//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.compose.shopping.graph](../index.md)/[OutlayGraphViewModel](index.md)

# OutlayGraphViewModel

[androidJvm]\
class [OutlayGraphViewModel](index.md)@Injectconstructor(getSkuSumMontUseCase: [GetSkuSumMontUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-sum-mont-use-case/index.md), sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [OutlayGraphViewModel](-outlay-graph-view-model.md) | [androidJvm]<br>@Inject<br>constructor(getSkuSumMontUseCase: [GetSkuSumMontUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-sum-mont-use-case/index.md), sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowSkuMonth](flow-sku-month.md) | [androidJvm]<br>val [flowSkuMonth](flow-sku-month.md): MutableStateFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [flowSkuSumMonthModelList](flow-sku-sum-month-model-list.md) | [androidJvm]<br>val [flowSkuSumMonthModelList](flow-sku-sum-month-model-list.md): MutableStateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuSumMonthModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-sum-month-model/index.md)&gt;&gt; |
| [flowSkuYear](flow-sku-year.md) | [androidJvm]<br>val [flowSkuYear](flow-sku-year.md): MutableStateFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
