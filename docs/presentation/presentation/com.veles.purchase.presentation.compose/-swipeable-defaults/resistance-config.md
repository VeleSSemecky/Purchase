//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableDefaults](index.md)/[resistanceConfig](resistance-config.md)

# resistanceConfig

[androidJvm]\
fun [resistanceConfig](resistance-config.md)(anchors: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;, factorAtMin: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = StandardResistanceFactor, factorAtMax: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = StandardResistanceFactor): [ResistanceConfig](../-resistance-config/index.md)?

The default resistance config used by [swipeable](../swipeable.md).

This returns `null` if there is one anchor. If there are at least two anchors, it returns a [ResistanceConfig](../-resistance-config/index.md) with the resistance basis equal to the distance between the two bounds.
