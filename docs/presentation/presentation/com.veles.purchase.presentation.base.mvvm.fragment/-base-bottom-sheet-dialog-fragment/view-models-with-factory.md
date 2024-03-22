//[presentation](../../../index.md)/[com.veles.purchase.presentation.base.mvvm.fragment](../index.md)/[BaseBottomSheetDialogFragment](index.md)/[viewModelsWithFactory](view-models-with-factory.md)

# viewModelsWithFactory

[androidJvm]\

@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)

inline fun &lt;[VM](view-models-with-factory.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [viewModelsWithFactory](view-models-with-factory.md)(noinline ownerProducer: () -&gt; [ViewModelStoreOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelStoreOwner.html) = { this }): [Lazy](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-lazy/index.html)&lt;[VM](view-models-with-factory.md)&gt;
