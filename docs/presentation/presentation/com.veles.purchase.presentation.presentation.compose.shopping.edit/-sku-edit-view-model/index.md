//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.compose.shopping.edit](../index.md)/[SkuEditViewModel](index.md)

# SkuEditViewModel

[androidJvm]\
class [SkuEditViewModel](index.md)@Injectconstructor(setSkuUseCase: [SetSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-set-sku-use-case/index.md), getSkuUseCase: [GetSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-use-case/index.md), getSkuPhotoUseCase: [GetSkuPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-photo-use-case/index.md), skuEditFragmentArgs: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [SkuEditViewModel](-sku-edit-view-model.md) | [androidJvm]<br>@Inject<br>constructor(setSkuUseCase: [SetSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-set-sku-use-case/index.md), getSkuUseCase: [GetSkuUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-use-case/index.md), getSkuPhotoUseCase: [GetSkuPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.sku/-get-sku-photo-use-case/index.md), skuEditFragmentArgs: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowSkuComment](flow-sku-comment.md) | [androidJvm]<br>val [flowSkuComment](flow-sku-comment.md): MutableStateFlow&lt;[TextFieldModel](../../com.veles.purchase.presentation.model.core/-text-field-model/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |
| [flowSkuCurrency](flow-sku-currency.md) | [androidJvm]<br>val [flowSkuCurrency](flow-sku-currency.md): MutableStateFlow&lt;[Currency](https://developer.android.com/reference/kotlin/android/icu/util/Currency.html)&gt; |
| [flowSkuLocalData](flow-sku-local-data.md) | [androidJvm]<br>val [flowSkuLocalData](flow-sku-local-data.md): MutableStateFlow&lt;[TextFieldModel](../../com.veles.purchase.presentation.model.core/-text-field-model/index.md)&lt;[LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)&gt;&gt; |
| [flowSkuName](flow-sku-name.md) | [androidJvm]<br>val [flowSkuName](flow-sku-name.md): MutableStateFlow&lt;[TextFieldModel](../../com.veles.purchase.presentation.model.core/-text-field-model/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |
| [flowSkuPhotoEntityList](flow-sku-photo-entity-list.md) | [androidJvm]<br>val [flowSkuPhotoEntityList](flow-sku-photo-entity-list.md): MutableStateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SkuPhotoModel](../../../../domain/domain/com.veles.purchase.domain.model/-sku-photo-model/index.md)&gt;&gt; |
| [flowSkuPrice](flow-sku-price.md) | [androidJvm]<br>val [flowSkuPrice](flow-sku-price.md): MutableStateFlow&lt;[TextFieldModel](../../com.veles.purchase.presentation.model.core/-text-field-model/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [save](save.md) | [androidJvm]<br>fun [save](save.md)(onSuccess: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |
| [setSkuComment](set-sku-comment.md) | [androidJvm]<br>fun [setSkuComment](set-sku-comment.md)(comment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [setSkuLocalData](set-sku-local-data.md) | [androidJvm]<br>fun [setSkuLocalData](set-sku-local-data.md)(data: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): Job |
| [setSkuName](set-sku-name.md) | [androidJvm]<br>fun [setSkuName](set-sku-name.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [setSkuPhotoEntity](set-sku-photo-entity.md) | [androidJvm]<br>fun [setSkuPhotoEntity](set-sku-photo-entity.md)(photoUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)): Job |
| [setSkuPrice](set-sku-price.md) | [androidJvm]<br>fun [setSkuPrice](set-sku-price.md)(price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
