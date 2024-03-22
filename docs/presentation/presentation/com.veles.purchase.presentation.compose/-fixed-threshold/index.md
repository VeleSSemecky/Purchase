//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[FixedThreshold](index.md)

# FixedThreshold

@[Immutable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Immutable.html)

data class [FixedThreshold](index.md)(offset: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)) : [ThresholdConfig](../-threshold-config/index.md)

A fixed threshold will be at an [offset](../../../../presentation/com.veles.purchase.presentation.compose/-fixed-threshold/offset.md) away from the first anchor.

#### Parameters

androidJvm

| | |
|---|---|
| offset | The offset (in dp) that the threshold will be at. |

## Constructors

| | |
|---|---|
| [FixedThreshold](-fixed-threshold.md) | [androidJvm]<br>constructor(offset: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)) |

## Functions

| Name | Summary |
|---|---|
| [computeThreshold](compute-threshold.md) | [androidJvm]<br>open override fun [Density](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Density.html).[computeThreshold](compute-threshold.md)(fromValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), toValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>Compute the value of the threshold (in pixels), once the values of the anchors are known. |
