//[presentation](../../index.md)/[com.veles.purchase.presentation.compose.search](index.md)/[DefaultAppBar](-default-app-bar.md)

# DefaultAppBar

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [DefaultAppBar](-default-app-bar.md)(searchWidgetState: [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[SearchWidgetState](-search-widget-state/index.md)&gt; = remember {
        mutableStateOf(value = SearchWidgetState.CLOSED)
    }, title: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)(searchWidgetState: [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[SearchWidgetState](-search-widget-state/index.md)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, navigationIcon: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)(searchWidgetState: [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[SearchWidgetState](-search-widget-state/index.md)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, actions: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)[RowScope](https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/RowScope.html).(searchWidgetState: [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[SearchWidgetState](-search-widget-state/index.md)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {})
