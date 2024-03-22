//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableState](index.md)/[targetValue](target-value.md)

# targetValue

[androidJvm]\
val [targetValue](target-value.md): [T](index.md)

The target value of the state.

If a swipe is in progress, this is the value that the [swipeable](../swipeable.md) would animate to if the swipe finished. If an animation is running, this is the target value of that animation. Finally, if no swipe or animation is in progress, this is the same as the [currentValue](current-value.md).
