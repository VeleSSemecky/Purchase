//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableState](index.md)/[offset](offset.md)

# offset

[androidJvm]\
val [offset](offset.md): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;

The current position (in pixels) of the [swipeable](../swipeable.md).

You should use this state to offset your content accordingly. The recommended way is to use `Modifier.offsetPx`. This includes the resistance by default, if resistance is enabled.
