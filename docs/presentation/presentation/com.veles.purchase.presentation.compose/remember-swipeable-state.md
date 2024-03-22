//[presentation](../../index.md)/[com.veles.purchase.presentation.compose](index.md)/[rememberSwipeableState](remember-swipeable-state.md)

# rememberSwipeableState

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun &lt;[T](remember-swipeable-state.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [rememberSwipeableState](remember-swipeable-state.md)(initialValue: [T](remember-swipeable-state.md), animationSpec: [AnimationSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/AnimationSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; = SwipeableDefaults.AnimationSpec, confirmStateChange: (newValue: [T](remember-swipeable-state.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true }): [SwipeableState](-swipeable-state/index.md)&lt;[T](remember-swipeable-state.md)&gt;

Create and [remember](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary.html) a [SwipeableState](-swipeable-state/index.md) with the default animation clock.

#### Parameters

androidJvm

| | |
|---|---|
| initialValue | The initial value of the state. |
| animationSpec | The default animation that will be used to animate to a new state. |
| confirmStateChange | Optional callback invoked to confirm or veto a pending state change. |
