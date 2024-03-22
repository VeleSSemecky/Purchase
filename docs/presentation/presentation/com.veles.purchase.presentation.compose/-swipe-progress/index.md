//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeProgress](index.md)

# SwipeProgress

@[Immutable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Immutable.html)

class [SwipeProgress](index.md)&lt;[T](index.md)&gt;(val from: [T](index.md), val to: [T](index.md), val fraction: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html))

Collects information about the ongoing swipe or animation in [swipeable](../swipeable.md).

To access this information, use [SwipeableState.progress](../-swipeable-state/progress.md).

#### Parameters

androidJvm

| | |
|---|---|
| from | The state corresponding to the anchor we are moving away from. |
| to | The state corresponding to the anchor we are moving towards. |
| fraction | The fraction that the current position represents between [from](from.md) and [to](to.md). Must be between `0` and `1`. |

## Constructors

| | |
|---|---|
| [SwipeProgress](-swipe-progress.md) | [androidJvm]<br>constructor(from: [T](index.md), to: [T](index.md), fraction: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [fraction](fraction.md) | [androidJvm]<br>val [fraction](fraction.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [from](from.md) | [androidJvm]<br>val [from](from.md): [T](index.md) |
| [to](to.md) | [androidJvm]<br>val [to](to.md): [T](index.md) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [androidJvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [androidJvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [androidJvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
