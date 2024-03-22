//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose.autocomplete](../index.md)/[AutoCompleteScope](index.md)

# AutoCompleteScope

@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)

interface [AutoCompleteScope](index.md)&lt;[T](index.md) : [AutoCompleteEntity](../-auto-complete-entity/index.md)&gt; : [AutoCompleteDesignScope](../-auto-complete-design-scope/index.md)

#### Inheritors

| |
|---|
| [AutoCompleteState](../-auto-complete-state/index.md) |

## Properties

| Name | Summary |
|---|---|
| [boxBorderStroke](../-auto-complete-design-scope/box-border-stroke.md) | [androidJvm]<br>abstract var [boxBorderStroke](../-auto-complete-design-scope/box-border-stroke.md): [BorderStroke](https://developer.android.com/reference/kotlin/androidx/compose/foundation/BorderStroke.html) |
| [boxMaxHeight](../-auto-complete-design-scope/box-max-height.md) | [androidJvm]<br>abstract var [boxMaxHeight](../-auto-complete-design-scope/box-max-height.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |
| [boxShape](../-auto-complete-design-scope/box-shape.md) | [androidJvm]<br>abstract var [boxShape](../-auto-complete-design-scope/box-shape.md): [Shape](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Shape.html) |
| [boxWidthPercentage](../-auto-complete-design-scope/box-width-percentage.md) | [androidJvm]<br>abstract var [boxWidthPercentage](../-auto-complete-design-scope/box-width-percentage.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [isSearching](is-searching.md) | [androidJvm]<br>abstract var [isSearching](is-searching.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldWrapContentHeight](../-auto-complete-design-scope/should-wrap-content-height.md) | [androidJvm]<br>abstract var [shouldWrapContentHeight](../-auto-complete-design-scope/should-wrap-content-height.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Functions

| Name | Summary |
|---|---|
| [filter](filter.md) | [androidJvm]<br>abstract fun [filter](filter.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [onItemSelected](on-item-selected.md) | [androidJvm]<br>abstract fun [onItemSelected](on-item-selected.md)(block: [ItemSelected](../../../../presentation/com.veles.purchase.presentation.compose.autocomplete/-item-selected/index.md)&lt;[T](index.md)&gt; = {}) |
