//[presentation](../../index.md)/[com.veles.purchase.presentation.compose](index.md)/[SwipeToDismiss](-swipe-to-dismiss.md)

# SwipeToDismiss

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [SwipeToDismiss](-swipe-to-dismiss.md)(state: [DismissState](-dismiss-state/index.md), modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, directions: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[DismissDirection](-dismiss-direction/index.md)&gt; = setOf(EndToStart, StartToEnd), dismissThresholds: ([DismissDirection](-dismiss-direction/index.md)) -&gt; [ThresholdConfig](-threshold-config/index.md) = {
        FractionalThreshold(
            0.5f
        )
    }, background: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)[RowScope](https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/RowScope.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), dismissContent: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)[RowScope](https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/RowScope.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

A composable that can be dismissed by swiping left or right.

#### Parameters

androidJvm

| | |
|---|---|
| state | The state of this component. |
| modifier | Optional [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) for this component. |
| directions | The set of directions in which the component can be dismissed. |
| dismissThresholds | The thresholds the item needs to be swiped in order to be dismissed. |
| background | A composable that is stacked behind the content and is exposed when the content is swiped. You can/should use the [state](-swipe-to-dismiss.md) to have different backgrounds on each side. |
| dismissContent | The content that can be dismissed. |
