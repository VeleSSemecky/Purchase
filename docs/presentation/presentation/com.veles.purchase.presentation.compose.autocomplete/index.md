//[presentation](../../index.md)/[com.veles.purchase.presentation.compose.autocomplete](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AutoCompleteDesignScope](-auto-complete-design-scope/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>interface [AutoCompleteDesignScope](-auto-complete-design-scope/index.md) |
| [AutoCompleteEntity](-auto-complete-entity/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>interface [AutoCompleteEntity](-auto-complete-entity/index.md) |
| [AutoCompleteScope](-auto-complete-scope/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>interface [AutoCompleteScope](-auto-complete-scope/index.md)&lt;[T](-auto-complete-scope/index.md) : [AutoCompleteEntity](-auto-complete-entity/index.md)&gt; : [AutoCompleteDesignScope](-auto-complete-design-scope/index.md) |
| [AutoCompleteState](-auto-complete-state/index.md) | [androidJvm]<br>class [AutoCompleteState](-auto-complete-state/index.md)&lt;[T](-auto-complete-state/index.md) : [AutoCompleteEntity](-auto-complete-entity/index.md)&gt;(startItems: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](-auto-complete-state/index.md)&gt;) : [AutoCompleteScope](-auto-complete-scope/index.md)&lt;[T](-auto-complete-state/index.md)&gt; |
| [CustomFilter](-custom-filter/index.md) | [androidJvm]<br>typealias [CustomFilter](-custom-filter/index.md)&lt;[T](-custom-filter/index.md)&gt; = ([T](-custom-filter/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [ValueAutoCompleteEntity](-value-auto-complete-entity/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>interface [ValueAutoCompleteEntity](-value-auto-complete-entity/index.md)&lt;[T](-value-auto-complete-entity/index.md)&gt; : [AutoCompleteEntity](-auto-complete-entity/index.md) |

## Properties

| Name | Summary |
|---|---|
| [AutoCompleteBoxTag](-auto-complete-box-tag.md) | [androidJvm]<br>const val [AutoCompleteBoxTag](-auto-complete-box-tag.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [AutoCompleteSearchBarTag](-auto-complete-search-bar-tag.md) | [androidJvm]<br>const val [AutoCompleteSearchBarTag](-auto-complete-search-bar-tag.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [asAutoCompleteEntities](as-auto-complete-entities.md) | [androidJvm]<br>fun &lt;[T](as-auto-complete-entities.md)&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](as-auto-complete-entities.md)&gt;.[asAutoCompleteEntities](as-auto-complete-entities.md)(filter: [CustomFilter](-custom-filter/index.md)&lt;[T](as-auto-complete-entities.md)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ValueAutoCompleteEntity](-value-auto-complete-entity/index.md)&lt;[T](as-auto-complete-entities.md)&gt;&gt; |
| [AutoCompleteBox](-auto-complete-box.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun &lt;[T](-auto-complete-box.md) : [AutoCompleteEntity](-auto-complete-entity/index.md)&gt; [AutoCompleteBox](-auto-complete-box.md)(items: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](-auto-complete-box.md)&gt;, itemContent: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)([T](-auto-complete-box.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), content: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)[AutoCompleteScope](-auto-complete-scope/index.md)&lt;[T](-auto-complete-box.md)&gt;.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [TextSearchBar](-text-search-bar.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [TextSearchBar](-text-search-bar.md)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onDoneActionClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, onClearClick: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, onFocusChanged: ([FocusState](https://developer.android.com/reference/kotlin/androidx/compose/ui/focus/FocusState.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, onValueChanged: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), colors: [TextFieldColors](https://developer.android.com/reference/kotlin/androidx/compose/material3/TextFieldColors.html), textStyle: [TextStyle](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/TextStyle.html), label: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? = null) |
