//[presentation](../../../index.md)/[com.veles.purchase.presentation.data.bus](../index.md)/[SharedFlowBusImpl](index.md)

# SharedFlowBusImpl

[androidJvm]\
class [SharedFlowBusImpl](index.md)@Injectconstructor : [SharedFlowBus](../-shared-flow-bus/index.md)

## Constructors

| | |
|---|---|
| [SharedFlowBusImpl](-shared-flow-bus-impl.md) | [androidJvm]<br>@Inject<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [getSharedFlow](get-shared-flow.md) | [androidJvm]<br>open override fun &lt;[T](get-shared-flow.md) : [Event](../-event/index.md)&gt; [getSharedFlow](get-shared-flow.md)(clazz: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[T](get-shared-flow.md)&gt;): Flow&lt;[T](get-shared-flow.md)&gt; |
| [setSharedFlow](set-shared-flow.md) | [androidJvm]<br>open suspend override fun &lt;[T](set-shared-flow.md) : [Event](../-event/index.md)&gt; [setSharedFlow](set-shared-flow.md)(state: [T](set-shared-flow.md)) |
| [setSharedFlowNotNull](set-shared-flow-not-null.md) | [androidJvm]<br>open suspend override fun &lt;[T](set-shared-flow-not-null.md) : [Event](../-event/index.md)&gt; [setSharedFlowNotNull](set-shared-flow-not-null.md)(state: [T](set-shared-flow-not-null.md)?) |
