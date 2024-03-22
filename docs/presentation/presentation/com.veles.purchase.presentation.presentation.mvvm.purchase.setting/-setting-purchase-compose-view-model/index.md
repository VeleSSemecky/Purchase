//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.setting](../index.md)/[SettingPurchaseComposeViewModel](index.md)

# SettingPurchaseComposeViewModel

[androidJvm]\
class [SettingPurchaseComposeViewModel](index.md)@Injectconstructor(getSettingUseCase: [GetSettingUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.setting/-get-setting-use-case/index.md), setSettingUseCase: [SetSettingUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.setting/-set-setting-use-case/index.md), router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [SettingPurchaseComposeViewModel](-setting-purchase-compose-view-model.md) | [androidJvm]<br>@Inject<br>constructor(getSettingUseCase: [GetSettingUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.setting/-get-setting-use-case/index.md), setSettingUseCase: [SetSettingUseCase](../../../../domain/domain/com.veles.purchase.domain.usecase.setting/-set-setting-use-case/index.md), router: [Router](../../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowAllCorner](flow-all-corner.md) | [androidJvm]<br>val [flowAllCorner](flow-all-corner.md): StateFlow&lt;[CornerSetting](../../com.veles.purchase.presentation.model.setting/-corner-setting/index.md)&gt; |
| [flowPurchaseSetting](flow-purchase-setting.md) | [androidJvm]<br>val [flowPurchaseSetting](flow-purchase-setting.md): StateFlow&lt;[PurchaseSetting](../../../../domain/domain/com.veles.purchase.domain.model.setting/-purchase-setting/index.md)&gt; |
| [flowSideCorner](flow-side-corner.md) | [androidJvm]<br>val [flowSideCorner](flow-side-corner.md): StateFlow&lt;[CornerSetting](../../com.veles.purchase.presentation.model.setting/-corner-setting/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [onAllCornerChanged](on-all-corner-changed.md) | [androidJvm]<br>fun [onAllCornerChanged](on-all-corner-changed.md)(size: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): Job |
| [onIsShowImageChanged](on-is-show-image-changed.md) | [androidJvm]<br>fun [onIsShowImageChanged](on-is-show-image-changed.md)(isShowImage: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): Job |
| [onIsSymmetryChanged](on-is-symmetry-changed.md) | [androidJvm]<br>fun [onIsSymmetryChanged](on-is-symmetry-changed.md)(isSymmetry: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): Job |
| [onSaveSettingsPurchaseChanged](on-save-settings-purchase-changed.md) | [androidJvm]<br>fun [onSaveSettingsPurchaseChanged](on-save-settings-purchase-changed.md)(): Job |
| [onSideCornerChanged](on-side-corner-changed.md) | [androidJvm]<br>fun [onSideCornerChanged](on-side-corner-changed.md)(topStart: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = flowSideCorner.value.topStart, topEnd: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = flowSideCorner.value.topEnd, bottomStart: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = flowSideCorner.value.bottomStart, bottomEnd: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = flowSideCorner.value.bottomEnd): Job |
| [onSizeTypeChanged](on-size-type-changed.md) | [androidJvm]<br>fun [onSizeTypeChanged](on-size-type-changed.md)(sizeType: [SizeType](../../../../domain/domain/com.veles.purchase.domain.model.setting/-size-type/index.md)): Job |
| [onTypeShapeChanged](on-type-shape-changed.md) | [androidJvm]<br>fun [onTypeShapeChanged](on-type-shape-changed.md)(shapeType: [ShapeType](../../../../domain/domain/com.veles.purchase.domain.model.setting/-shape-type/index.md)): Job |
