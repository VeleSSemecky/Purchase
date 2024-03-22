//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[SwipeableState](index.md)/[SwipeableState](-swipeable-state.md)

# SwipeableState

[androidJvm]\
constructor(initialValue: [T](index.md), animationSpec: [AnimationSpec](https://developer.android.com/reference/kotlin/androidx/compose/animation/core/AnimationSpec.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt; = SwipeableDefaults.AnimationSpec, confirmStateChange: (newValue: [T](index.md)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true })

#### Parameters

androidJvm

| | |
|---|---|
| initialValue | The initial value of the state. |
| animationSpec | The default animation that will be used to animate to a new state. |
| confirmStateChange | Optional callback invoked to confirm or veto a pending state change. |
