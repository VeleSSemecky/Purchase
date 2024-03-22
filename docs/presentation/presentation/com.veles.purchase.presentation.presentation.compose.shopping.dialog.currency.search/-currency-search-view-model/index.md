//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search](../index.md)/[CurrencySearchViewModel](index.md)

# CurrencySearchViewModel

[androidJvm]\
class [CurrencySearchViewModel](index.md)@Injectconstructor(sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [CurrencySearchViewModel](-currency-search-view-model.md) | [androidJvm]<br>@Inject<br>constructor(sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->) |

## Properties

| Name | Summary |
|---|---|
| [flowCurrencyModelList](flow-currency-model-list.md) | [androidJvm]<br>val [flowCurrencyModelList](flow-currency-model-list.md): MutableStateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[CurrencyModel](../../com.veles.purchase.presentation.model.currency/-currency-model/index.md)&gt;&gt; |
| [flowValueSearch](flow-value-search.md) | [androidJvm]<br>val [flowValueSearch](flow-value-search.md): MutableStateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [save](save.md) | [androidJvm]<br>fun [save](save.md)(): Job |
| [setCurrency](set-currency.md) | [androidJvm]<br>fun [setCurrency](set-currency.md)(currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [setCurrencyModelList](set-currency-model-list.md) | [androidJvm]<br>fun [setCurrencyModelList](set-currency-model-list.md)(currencyModelList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[CurrencyModel](../../com.veles.purchase.presentation.model.currency/-currency-model/index.md)&gt;): Job |
| [setValueSearch](set-value-search.md) | [androidJvm]<br>fun [setValueSearch](set-value-search.md)(valueSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
