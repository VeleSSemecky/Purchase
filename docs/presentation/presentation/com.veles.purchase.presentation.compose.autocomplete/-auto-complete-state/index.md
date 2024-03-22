//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose.autocomplete](../index.md)/[AutoCompleteState](index.md)

# AutoCompleteState

[androidJvm]\
class [AutoCompleteState](index.md)&lt;[T](index.md) : [AutoCompleteEntity](../-auto-complete-entity/index.md)&gt;(startItems: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt;) : [AutoCompleteScope](../-auto-complete-scope/index.md)&lt;[T](index.md)&gt;

## Constructors

| | |
|---|---|
| [AutoCompleteState](-auto-complete-state.md) | [androidJvm]<br>constructor(startItems: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt;) |

## Properties

| Name | Summary |
|---|---|
| [boxBorderStroke](box-border-stroke.md) | [androidJvm]<br>open override var [boxBorderStroke](box-border-stroke.md): [BorderStroke](https://developer.android.com/reference/kotlin/androidx/compose/foundation/BorderStroke.html) |
| [boxMaxHeight](box-max-height.md) | [androidJvm]<br>open override var [boxMaxHeight](box-max-height.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |
| [boxShape](box-shape.md) | [androidJvm]<br>open override var [boxShape](box-shape.md): [Shape](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Shape.html) |
| [boxWidthPercentage](box-width-percentage.md) | [androidJvm]<br>open override var [boxWidthPercentage](box-width-percentage.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [filteredItems](filtered-items.md) | [androidJvm]<br>var [filteredItems](filtered-items.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](index.md)&gt; |
| [isSearching](is-searching.md) | [androidJvm]<br>open override var [isSearching](is-searching.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldWrapContentHeight](should-wrap-content-height.md) | [androidJvm]<br>open override var [shouldWrapContentHeight](should-wrap-content-height.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Functions

| Name | Summary |
|---|---|
| [filter](filter.md) | [androidJvm]<br>open override fun [filter](filter.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [onItemSelected](on-item-selected.md) | [androidJvm]<br>open override fun [onItemSelected](on-item-selected.md)(block: [ItemSelected](../../../../presentation/com.veles.purchase.presentation.compose.autocomplete/-item-selected/index.md)&lt;[T](index.md)&gt;) |
| [selectItem](select-item.md) | [androidJvm]<br>fun [selectItem](select-item.md)(item: [T](index.md)) |
