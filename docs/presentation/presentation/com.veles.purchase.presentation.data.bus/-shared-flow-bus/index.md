//[presentation](../../../index.md)/[com.veles.purchase.presentation.data.bus](../index.md)/[SharedFlowBus](index.md)

# SharedFlowBus

interface [SharedFlowBus](index.md)

#### Inheritors

| |
|---|
| [SharedFlowBusImpl](../-shared-flow-bus-impl/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getSharedFlow](get-shared-flow.md) | [androidJvm]<br>abstract fun &lt;[T](get-shared-flow.md) : [Event](../-event/index.md)&gt; [getSharedFlow](get-shared-flow.md)(clazz: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[T](get-shared-flow.md)&gt;): Flow&lt;[T](get-shared-flow.md)&gt; |
| [setSharedFlow](set-shared-flow.md) | [androidJvm]<br>abstract suspend fun &lt;[T](set-shared-flow.md) : [Event](../-event/index.md)&gt; [setSharedFlow](set-shared-flow.md)(state: [T](set-shared-flow.md)) |
| [setSharedFlowNotNull](set-shared-flow-not-null.md) | [androidJvm]<br>abstract suspend fun &lt;[T](set-shared-flow-not-null.md) : [Event](../-event/index.md)&gt; [setSharedFlowNotNull](set-shared-flow-not-null.md)(state: [T](set-shared-flow-not-null.md)?) |
