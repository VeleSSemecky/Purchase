//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableDefaults](index.md)

# SwipeableDefaults

[androidJvm]\
object [SwipeableDefaults](index.md)

Contains useful defaults for [swipeable](../swipeable.md) and [SwipeableState](../-swipeable-state/index.md).

## Properties

| Name | Summary |
|---|---|
| [AnimationSpec](-animation-spec.md) | [androidJvm]<br>val [AnimationSpec](-animation-spec.md): [SpringSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/SpringSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;<br>The default animation used by [SwipeableState](../-swipeable-state/index.md). |
| [StandardResistanceFactor](-standard-resistance-factor.md) | [androidJvm]<br>const val [StandardResistanceFactor](-standard-resistance-factor.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 10.0f<br>A standard resistance factor which indicates that the user has run out of things to see. |
| [StiffResistanceFactor](-stiff-resistance-factor.md) | [androidJvm]<br>const val [StiffResistanceFactor](-stiff-resistance-factor.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 20.0f<br>A stiff resistance factor which indicates that swiping isn't available right now. |
| [VelocityThreshold](-velocity-threshold.md) | [androidJvm]<br>val [VelocityThreshold](-velocity-threshold.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>The default velocity threshold (1.8 dp per millisecond) used by [swipeable](../swipeable.md). |

## Functions

| Name | Summary |
|---|---|
| [resistanceConfig](resistance-config.md) | [androidJvm]<br>fun [resistanceConfig](resistance-config.md)(anchors: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;, factorAtMin: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = StandardResistanceFactor, factorAtMax: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = StandardResistanceFactor): [ResistanceConfig](../-resistance-config/index.md)?<br>The default resistance config used by [swipeable](../swipeable.md). |
