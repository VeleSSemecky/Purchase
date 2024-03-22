//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit](../index.md)/[EditCollectionComposeViewModel](index.md)

# EditCollectionComposeViewModel

[androidJvm]\
class [EditCollectionComposeViewModel](index.md)@Injectconstructor(args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, setCollectionPurchaseUseCase: [SetCollectionPurchaseUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-set-collection-purchase-use-case/index.md), firebaseFirestorePurchaseCollectionUseCase: [FirebaseFirestorePurchaseCollectionUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-firebase-firestore-purchase-collection-use-case/index.md), userUseCase: [UserUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.user/-user-use-case/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [EditCollectionComposeViewModel](-edit-collection-compose-view-model.md) | [androidJvm]<br>@Inject<br>constructor(args: <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"","classNames":"<Error class: unknown class>","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->&lt;Error class: unknown class&gt;<!--- --->, setCollectionPurchaseUseCase: [SetCollectionPurchaseUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-set-collection-purchase-use-case/index.md), firebaseFirestorePurchaseCollectionUseCase: [FirebaseFirestorePurchaseCollectionUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.collection/-firebase-firestore-purchase-collection-use-case/index.md), userUseCase: [UserUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.user/-user-use-case/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowCollectionName](flow-collection-name.md) | [androidJvm]<br>val [flowCollectionName](flow-collection-name.md): MutableStateFlow&lt;[TextFieldModel](../../com.veles.purchase.presentation.model.core/-text-field-model/index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |
| [flowListUserChecked](flow-list-user-checked.md) | [androidJvm]<br>val [flowListUserChecked](flow-list-user-checked.md): MutableStateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserCheckedUI](../../com.veles.purchase.presentation.model.user/-user-checked-u-i/index.md)&gt;&gt; |
| [flowProgress](flow-progress.md) | [androidJvm]<br>val [flowProgress](flow-progress.md): MutableStateFlow&lt;[Progress](../../com.veles.purchase.presentation.model.progress/-progress/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [onUpdateCheck](on-update-check.md) | [androidJvm]<br>fun [onUpdateCheck](on-update-check.md)(index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), item: [UserCheckedUI](../../com.veles.purchase.presentation.model.user/-user-checked-u-i/index.md)): Job |
| [save](save.md) | [androidJvm]<br>fun [save](save.md)(onSuccess: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |
| [setCollectionName](set-collection-name.md) | [androidJvm]<br>fun [setCollectionName](set-collection-name.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Job |
