//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[ResistanceConfig](index.md)

# ResistanceConfig

@[Immutable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Immutable.html)

class [ResistanceConfig](index.md)(val basis: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val factorAtMin: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = SwipeableDefaults.StandardResistanceFactor, val factorAtMax: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = SwipeableDefaults.StandardResistanceFactor)

Specifies how resistance is calculated in [swipeable](../swipeable.md).

There are two things needed to calculate resistance: the resistance basis determines how much overflow will be consumed to achieve maximum resistance, and the resistance factor determines the amount of resistance (the larger the resistance factor, the stronger the resistance).

The resistance basis is usually either the size of the component which [swipeable](../swipeable.md) is applied to, or the distance between the minimum and maximum anchors. For a constructor in which the resistance basis defaults to the latter, consider using [resistanceConfig](../-swipeable-defaults/resistance-config.md).

You may specify different resistance factors for each bound. Consider using one of the default resistance factors in [SwipeableDefaults](../-swipeable-defaults/index.md): `StandardResistanceFactor` to convey that the user has run out of things to see, and `StiffResistanceFactor` to convey that the user cannot swipe this right now. Also, you can set either factor to 0 to disable resistance at that bound.

#### Parameters

androidJvm

| | |
|---|---|
| basis | Specifies the maximum amount of overflow that will be consumed. Must be positive. |
| factorAtMin | The factor by which to scale the resistance at the minimum bound. Must not be negative. |
| factorAtMax | The factor by which to scale the resistance at the maximum bound. Must not be negative. |

## Constructors

| | |
|---|---|
| [ResistanceConfig](-resistance-config.md) | [androidJvm]<br>constructor(basis: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), factorAtMin: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = SwipeableDefaults.StandardResistanceFactor, factorAtMax: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = SwipeableDefaults.StandardResistanceFactor) |

## Properties

| Name | Summary |
|---|---|
| [basis](basis.md) | [androidJvm]<br>val [basis](basis.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [factorAtMax](factor-at-max.md) | [androidJvm]<br>val [factorAtMax](factor-at-max.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [factorAtMin](factor-at-min.md) | [androidJvm]<br>val [factorAtMin](factor-at-min.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |

## Functions

| Name | Summary |
|---|---|
| [computeResistance](compute-resistance.md) | [androidJvm]<br>fun [computeResistance](compute-resistance.md)(overflow: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [equals](equals.md) | [androidJvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [androidJvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [androidJvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
