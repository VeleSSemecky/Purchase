//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.edit](../index.md)/[EditPurchaseViewModel](index.md)

# EditPurchaseViewModel

[androidJvm]\
class [EditPurchaseViewModel](index.md)@Injectconstructor(args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), getPurchaseUseCase: [GetPurchaseUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.purchase/-get-purchase-use-case/index.md), getPhotoUseCase: [GetPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.storage/-get-photo-use-case/index.md), savePurchaseUseCase: [SavePurchaseUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.purchase/-save-purchase-use-case/index.md), router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [EditPurchaseViewModel](-edit-purchase-view-model.md) | [androidJvm]<br>@Inject<br>constructor(args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, sharedFlowBus: [SharedFlowBus](../../com.veles.purchase.presentation.data.bus/-shared-flow-bus/index.md), getPurchaseUseCase: [GetPurchaseUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.purchase/-get-purchase-use-case/index.md), getPhotoUseCase: [GetPhotoUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.storage/-get-photo-use-case/index.md), savePurchaseUseCase: [SavePurchaseUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.purchase/-save-purchase-use-case/index.md), router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowProgress](flow-progress.md) | [androidJvm]<br>val [flowProgress](flow-progress.md): MutableStateFlow&lt;[Progress](../../com.veles.purchase.presentation.model.progress/-progress/index.md)&gt; |
| [flowPurchaseComment](flow-purchase-comment.md) | [androidJvm]<br>val [flowPurchaseComment](flow-purchase-comment.md): StateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [flowPurchaseCurrency](flow-purchase-currency.md) | [androidJvm]<br>val [flowPurchaseCurrency](flow-purchase-currency.md): MutableStateFlow&lt;[Currency](https://developer.android.com/reference/kotlin/java/util/Currency.html)&gt; |
| [flowPurchaseIsChecked](flow-purchase-is-checked.md) | [androidJvm]<br>val [flowPurchaseIsChecked](flow-purchase-is-checked.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [flowPurchaseLocalData](flow-purchase-local-data.md) | [androidJvm]<br>val [flowPurchaseLocalData](flow-purchase-local-data.md): MutableStateFlow&lt;[LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)&gt; |
| [flowPurchaseName](flow-purchase-name.md) | [androidJvm]<br>val [flowPurchaseName](flow-purchase-name.md): StateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [flowPurchasePhotoModelList](flow-purchase-photo-model-list.md) | [androidJvm]<br>val [flowPurchasePhotoModelList](flow-purchase-photo-model-list.md): StateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)&gt;&gt; |
| [flowPurchasePrice](flow-purchase-price.md) | [androidJvm]<br>val [flowPurchasePrice](flow-purchase-price.md): StateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [apiDatabaseURL](api-database-u-r-l.md) | [androidJvm]<br>fun [apiDatabaseURL](api-database-u-r-l.md)(purchaseModel: [PurchasePhotoModel](../../../../domain/domain/com.veles.purchase.domain.model.purchase/-purchase-photo-model/index.md)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [onCheckedChange](on-checked-change.md) | [androidJvm]<br>fun [onCheckedChange](on-checked-change.md)(isChecked: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): Job |
| [onCommentChange](on-comment-change.md) | [androidJvm]<br>fun [onCommentChange](on-comment-change.md)(comment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [onPriceChange](on-price-change.md) | [androidJvm]<br>fun [onPriceChange](on-price-change.md)(price: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [onSaveClicked](on-save-clicked.md) | [androidJvm]<br>fun [onSaveClicked](on-save-clicked.md)(): Job |
| [onTitleChange](on-title-change.md) | [androidJvm]<br>fun [onTitleChange](on-title-change.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
| [setPurchaseLocalData](set-purchase-local-data.md) | [androidJvm]<br>fun [setPurchaseLocalData](set-purchase-local-data.md)(data: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): Job |
| [setPurchasePhotoModel](set-purchase-photo-model.md) | [androidJvm]<br>fun [setPurchasePhotoModel](set-purchase-photo-model.md)(photoUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)): Job |
