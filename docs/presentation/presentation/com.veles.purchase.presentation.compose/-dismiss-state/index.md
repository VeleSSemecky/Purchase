//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[DismissState](index.md)

# DismissState

class [DismissState](index.md)(initialValue: [DismissValue](../-dismiss-value/index.md), confirmStateChange: ([DismissValue](../-dismiss-value/index.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true }) : [SwipeableState](../-swipeable-state/index.md)&lt;[DismissValue](../-dismiss-value/index.md)&gt; 

State of the [SwipeToDismiss](../-swipe-to-dismiss.md) composable.

#### Parameters

androidJvm

| | |
|---|---|
| initialValue | The initial value of the state. |
| confirmStateChange | Optional callback invoked to confirm or veto a pending state change. |

## Constructors

| | |
|---|---|
| [DismissState](-dismiss-state.md) | [androidJvm]<br>constructor(initialValue: [DismissValue](../-dismiss-value/index.md), confirmStateChange: ([DismissValue](../-dismiss-value/index.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true }) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [currentValue](../-swipeable-state/current-value.md) | [androidJvm]<br>var [currentValue](../-swipeable-state/current-value.md): [DismissValue](../-dismiss-value/index.md)<br>The current value of the state. |
| [direction](../-swipeable-state/direction.md) | [androidJvm]<br>val [direction](../-swipeable-state/direction.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>The direction in which the [swipeable](../swipeable.md) is moving, relative to the current [currentValue](../-swipeable-state/current-value.md). |
| [dismissDirection](dismiss-direction.md) | [androidJvm]<br>val [dismissDirection](dismiss-direction.md): [DismissDirection](../-dismiss-direction/index.md)?<br>The direction (if any) in which the composable has been or is being dismissed. |
| [isAnimationRunning](../-swipeable-state/is-animation-running.md) | [androidJvm]<br>var [isAnimationRunning](../-swipeable-state/is-animation-running.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Whether the state is currently animating. |
| [offset](../-swipeable-state/offset.md) | [androidJvm]<br>val [offset](../-swipeable-state/offset.md): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;<br>The current position (in pixels) of the [swipeable](../swipeable.md). |
| [overflow](../-swipeable-state/overflow.md) | [androidJvm]<br>val [overflow](../-swipeable-state/overflow.md): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;<br>The amount by which the [swipeable](../swipeable.md) has been swiped past its bounds. |
| [progress](../-swipeable-state/progress.md) | [androidJvm]<br>val [progress](../-swipeable-state/progress.md): [SwipeProgress](../-swipe-progress/index.md)&lt;[DismissValue](../-dismiss-value/index.md)&gt;<br>Information about the ongoing swipe or animation, if any. See [SwipeProgress](../-swipe-progress/index.md) for details. |
| [targetValue](../-swipeable-state/target-value.md) | [androidJvm]<br>val [targetValue](../-swipeable-state/target-value.md): [DismissValue](../-dismiss-value/index.md)<br>The target value of the state. |

## Functions

| Name | Summary |
|---|---|
| [animateTo](index.md#-430819110%2FFunctions%2F-646359276) | [androidJvm]<br>suspend fun [animateTo](index.md#-430819110%2FFunctions%2F-646359276)(targetValue: [DismissValue](../-dismiss-value/index.md), anim: [AnimationSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/AnimationSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; = animationSpec)<br>Set the state to the target value by starting an animation. |
| [dismiss](dismiss.md) | [androidJvm]<br>suspend fun [dismiss](dismiss.md)(direction: [DismissDirection](../-dismiss-direction/index.md))<br>Dismiss the component in the given [direction](dismiss.md), with an animation and suspend. This method will throw CancellationException if the animation is interrupted |
| [isDismissed](is-dismissed.md) | [androidJvm]<br>fun [isDismissed](is-dismissed.md)(direction: [DismissDirection](../-dismiss-direction/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Whether the component has been dismissed in the given [direction](is-dismissed.md). |
| [performDrag](../-swipeable-state/perform-drag.md) | [androidJvm]<br>fun [performDrag](../-swipeable-state/perform-drag.md)(delta: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>Force [swipeable](../swipeable.md) to consume drag delta provided from outside of the regular [swipeable](../swipeable.md) gesture flow. |
| [performFling](../-swipeable-state/perform-fling.md) | [androidJvm]<br>suspend fun [performFling](../-swipeable-state/perform-fling.md)()<br>Perform fling with settling to one of the anchors which is determined by the given . Fling with settling [swipeable](../swipeable.md) will always consume all the velocity provided since it will settle at the anchor. |
| [reset](reset.md) | [androidJvm]<br>suspend fun [reset](reset.md)()<br>Reset the component to the default position with animation and suspend until it if fully reset or animation has been cancelled. This method will throw CancellationException if the animation is interrupted |
| [snapTo](index.md#2027622272%2FFunctions%2F-646359276) | [androidJvm]<br>suspend fun [snapTo](index.md#2027622272%2FFunctions%2F-646359276)(targetValue: [DismissValue](../-dismiss-value/index.md))<br>Set the state without any animation and suspend until it's set |
