//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableState](index.md)

# SwipeableState

@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)

open class [SwipeableState](index.md)&lt;[T](index.md)&gt;(initialValue: [T](index.md), animationSpec: [AnimationSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/AnimationSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; = SwipeableDefaults.AnimationSpec, confirmStateChange: (newValue: [T](index.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true })

State of the [swipeable](../swipeable.md) modifier.

This contains necessary information about any ongoing swipe or animation and provides methods to change the state either immediately or by starting an animation. To create and remember a [SwipeableState](index.md) with the default animation clock, use [rememberSwipeableState](../remember-swipeable-state.md).

#### Parameters

androidJvm

| | |
|---|---|
| initialValue | The initial value of the state. |
| animationSpec | The default animation that will be used to animate to a new state. |
| confirmStateChange | Optional callback invoked to confirm or veto a pending state change. |

#### Inheritors

| |
|---|
| [DismissState](../-dismiss-state/index.md) |

## Constructors

| | |
|---|---|
| [SwipeableState](-swipeable-state.md) | [androidJvm]<br>constructor(initialValue: [T](index.md), animationSpec: [AnimationSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/AnimationSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; = SwipeableDefaults.AnimationSpec, confirmStateChange: (newValue: [T](index.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true }) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [currentValue](current-value.md) | [androidJvm]<br>var [currentValue](current-value.md): [T](index.md)<br>The current value of the state. |
| [direction](direction.md) | [androidJvm]<br>val [direction](direction.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>The direction in which the [swipeable](../swipeable.md) is moving, relative to the current [currentValue](current-value.md). |
| [isAnimationRunning](is-animation-running.md) | [androidJvm]<br>var [isAnimationRunning](is-animation-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Whether the state is currently animating. |
| [offset](offset.md) | [androidJvm]<br>val [offset](offset.md): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;<br>The current position (in pixels) of the [swipeable](../swipeable.md). |
| [overflow](overflow.md) | [androidJvm]<br>val [overflow](overflow.md): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;<br>The amount by which the [swipeable](../swipeable.md) has been swiped past its bounds. |
| [progress](progress.md) | [androidJvm]<br>val [progress](progress.md): [SwipeProgress](../-swipe-progress/index.md)&lt;[T](index.md)&gt;<br>Information about the ongoing swipe or animation, if any. See [SwipeProgress](../-swipe-progress/index.md) for details. |
| [targetValue](target-value.md) | [androidJvm]<br>val [targetValue](target-value.md): [T](index.md)<br>The target value of the state. |

## Functions

| Name | Summary |
|---|---|
| [animateTo](animate-to.md) | [androidJvm]<br>suspend fun [animateTo](animate-to.md)(targetValue: [T](index.md), anim: [AnimationSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/AnimationSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; = animationSpec)<br>Set the state to the target value by starting an animation. |
| [performDrag](perform-drag.md) | [androidJvm]<br>fun [performDrag](perform-drag.md)(delta: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>Force [swipeable](../swipeable.md) to consume drag delta provided from outside of the regular [swipeable](../swipeable.md) gesture flow. |
| [performFling](perform-fling.md) | [androidJvm]<br>suspend fun [performFling](perform-fling.md)()<br>Perform fling with settling to one of the anchors which is determined by the given . Fling with settling [swipeable](../swipeable.md) will always consume all the velocity provided since it will settle at the anchor. |
| [snapTo](snap-to.md) | [androidJvm]<br>suspend fun [snapTo](snap-to.md)(targetValue: [T](index.md))<br>Set the state without any animation and suspend until it's set |
