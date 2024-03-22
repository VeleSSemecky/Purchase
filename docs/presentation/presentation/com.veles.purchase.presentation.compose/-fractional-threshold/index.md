//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[FractionalThreshold](index.md)

# FractionalThreshold

[androidJvm]\
@[Immutable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Immutable.html)

data class [FractionalThreshold](index.md)(fraction: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) : [ThresholdConfig](../-threshold-config/index.md)

Interface to compute a threshold between two anchors/states in a [swipeable](../swipeable.md).

To define a [ThresholdConfig](../-threshold-config/index.md), consider using [FixedThreshold](../-fixed-threshold/index.md) and [FractionalThreshold](index.md).

## Constructors

| | |
|---|---|
| [FractionalThreshold](-fractional-threshold.md) | [androidJvm]<br>constructor(fraction: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [computeThreshold](compute-threshold.md) | [androidJvm]<br>open override fun [Density](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Density.html).[computeThreshold](compute-threshold.md)(fromValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), toValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>Compute the value of the threshold (in pixels), once the values of the anchors are known. |
