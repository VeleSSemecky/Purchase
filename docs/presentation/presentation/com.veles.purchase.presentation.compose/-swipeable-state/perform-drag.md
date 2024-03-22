//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableState](index.md)/[performDrag](perform-drag.md)

# performDrag

[androidJvm]\
fun [performDrag](perform-drag.md)(delta: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)

Force [swipeable](../swipeable.md) to consume drag delta provided from outside of the regular [swipeable](../swipeable.md) gesture flow.

Note: This method performs generic drag and it won't settle to any particular anchor, * leaving swipeable in between anchors. When done dragging, [performFling](perform-fling.md) must be called as well to ensure swipeable will settle at the anchor.

In general cases, [swipeable](../swipeable.md) drags by itself when being swiped. This method is to be used for nested scroll logic that wraps the [swipeable](../swipeable.md). In nested scroll developer may want to force drag when the child scroll container reaches the bound.

#### Return

the amount of [delta](perform-drag.md) consumed

#### Parameters

androidJvm

| | |
|---|---|
| delta | delta in pixels to drag by |
