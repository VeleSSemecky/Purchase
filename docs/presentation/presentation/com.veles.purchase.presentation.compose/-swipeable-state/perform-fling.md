//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableState](index.md)/[performFling](perform-fling.md)

# performFling

[androidJvm]\
suspend fun [performFling](perform-fling.md)()

Perform fling with settling to one of the anchors which is determined by the given . Fling with settling [swipeable](../swipeable.md) will always consume all the velocity provided since it will settle at the anchor.

In general cases, [swipeable](../swipeable.md) flings by itself when being swiped. This method is to be used for nested scroll logic that wraps the [swipeable](../swipeable.md). In nested scroll developer may want to trigger settling fling when the child scroll container reaches the bound.

#### Return

the reason fling ended
