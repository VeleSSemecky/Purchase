//[presentation](../../../index.md)/[com.veles.purchase.presentation.compose](../index.md)/[ResistanceConfig](index.md)/[ResistanceConfig](-resistance-config.md)

# ResistanceConfig

[androidJvm]\
constructor(basis: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), factorAtMin: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = SwipeableDefaults.StandardResistanceFactor, factorAtMax: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = SwipeableDefaults.StandardResistanceFactor)

#### Parameters

androidJvm

| | |
|---|---|
| basis | Specifies the maximum amount of overflow that will be consumed. Must be positive. |
| factorAtMin | The factor by which to scale the resistance at the minimum bound. Must not be negative. |
| factorAtMax | The factor by which to scale the resistance at the maximum bound. Must not be negative. |
