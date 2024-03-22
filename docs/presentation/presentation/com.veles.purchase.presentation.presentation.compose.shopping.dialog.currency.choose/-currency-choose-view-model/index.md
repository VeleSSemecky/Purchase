//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose](../index.md)/[CurrencyChooseViewModel](index.md)

# CurrencyChooseViewModel

[androidJvm]\
class [CurrencyChooseViewModel](index.md)@Injectconstructor(sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [CurrencyChooseViewModel](-currency-choose-view-model.md) | [androidJvm]<br>@Inject<br>constructor(sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->) |

## Properties

| Name | Summary |
|---|---|
| [flowCurrency](flow-currency.md) | [androidJvm]<br>val [flowCurrency](flow-currency.md): MutableStateFlow&lt;[Currency](https://developer.android.com/reference/kotlin/java/util/Currency.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [save](save.md) | [androidJvm]<br>fun [save](save.md)(): Job |
| [setCurrency](set-currency.md) | [androidJvm]<br>fun [setCurrency](set-currency.md)(currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
