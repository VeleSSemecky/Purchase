//[presentation](../../../index.md)/[com.veles.purchase.presentation.base.mvvm.viewmodel](../index.md)/[ViewModelFactory](index.md)

# ViewModelFactory

[androidJvm]\
class [ViewModelFactory](index.md)@Injectconstructor(viewModels: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;out [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt;, @[JvmSuppressWildcards](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-suppress-wildcards/index.html)Provider&lt;[ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt;&gt;) : [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)

## Constructors

| | |
|---|---|
| [ViewModelFactory](-view-model-factory.md) | [androidJvm]<br>@Inject<br>constructor(viewModels: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;out [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt;, @[JvmSuppressWildcards](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-suppress-wildcards/index.html)Provider&lt;[ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt;&gt;) |

## Functions

| Name | Summary |
|---|---|
| [create](index.md#79759200%2FFunctions%2F-646359276) | [androidJvm]<br>open fun &lt;[T](index.md#79759200%2FFunctions%2F-646359276) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](index.md#79759200%2FFunctions%2F-646359276)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](index.md#79759200%2FFunctions%2F-646359276)&gt;, extras: [CreationExtras](https://developer.android.com/reference/kotlin/androidx/lifecycle/viewmodel/CreationExtras.html)): [T](index.md#79759200%2FFunctions%2F-646359276)<br>open override fun &lt;[T](create.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](create.md)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](create.md)&gt;): [T](create.md) |
