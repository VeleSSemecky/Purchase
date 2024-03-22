//[presentation](../../index.md)/[com.veles.purchase.presentation.compose](index.md)/[swipeable](swipeable.md)

# swipeable

[androidJvm]\
fun &lt;[T](swipeable.md)&gt; [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html).[swipeable](swipeable.md)(state: [SwipeableState](-swipeable-state/index.md)&lt;[T](swipeable.md)&gt;, anchors: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), [T](swipeable.md)&gt;, orientation: [Orientation](https://developer.android.com/reference/kotlin/androidx/compose/foundation/gestures/Orientation.html), enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, reverseDirection: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, interactionSource: [MutableInteractionSource](https://developer.android.com/reference/kotlin/androidx/compose/foundation/interaction/MutableInteractionSource.html)? = null, thresholds: (from: [T](swipeable.md), to: [T](swipeable.md)) -&gt; [ThresholdConfig](-threshold-config/index.md) = { _, _ -&gt; FixedThreshold(56.dp) }, resistance: [ResistanceConfig](-resistance-config/index.md)? = resistanceConfig(anchors.keys), velocityThreshold: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) = VelocityThreshold): [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html)

Enable swipe gestures between a set of predefined states.

To use this, you must provide a map of anchors (in pixels) to states (of type [T](swipeable.md)). Note that this map cannot be empty and cannot have two anchors mapped to the same state.

When a swipe is detected, the offset of the [SwipeableState](-swipeable-state/index.md) will be updated with the swipe delta. You should use this offset to move your content accordingly (see `Modifier.offsetPx`). When the swipe ends, the offset will be animated to one of the anchors and when that anchor is reached, the value of the [SwipeableState](-swipeable-state/index.md) will also be updated to the state corresponding to the new anchor. The target anchor is calculated based on the provided positional [thresholds](swipeable.md).

Swiping is constrained between the minimum and maximum anchors. If the user attempts to swipe past these bounds, a resistance effect will be applied by default. The amount of resistance at each edge is specified by the [resistance](swipeable.md) config. To disable all resistance, set it to `null`.

For an example of a [swipeable](swipeable.md) with three states, see:

#### Parameters

androidJvm

| | |
|---|---|
| T | The type of the state. |
| state | The state of the [swipeable](swipeable.md). |
| anchors | Pairs of anchors and states, used to map anchors to states and vice versa. |
| thresholds | Specifies where the thresholds between the states are. The thresholds will be used to determine which state to animate to when swiping stops. This is represented as a lambda that takes two states and returns the threshold between them in the form of a [ThresholdConfig](-threshold-config/index.md). Note that the order of the states corresponds to the swipe direction. |
| orientation | The orientation in which the [swipeable](swipeable.md) can be swiped. |
| enabled | Whether this [swipeable](swipeable.md) is enabled and should react to the user's input. |
| reverseDirection | Whether to reverse the direction of the swipe, so a top to bottom swipe will behave like bottom to top, and a left to right swipe will behave like right to left. |
| interactionSource | Optional [MutableInteractionSource](https://developer.android.com/reference/kotlin/androidx/compose/foundation/interaction/MutableInteractionSource.html) that will passed on to the internal [Modifier.draggable](https://developer.android.com/reference/kotlin/androidx/compose/foundation/gestures/package-summary.html). |
| resistance | Controls how much resistance will be applied when swiping past the bounds. |
| velocityThreshold | The threshold (in dp per second) that the end velocity has to exceed in order to animate to the next state, even if the positional [thresholds](swipeable.md) have not been reached. |
