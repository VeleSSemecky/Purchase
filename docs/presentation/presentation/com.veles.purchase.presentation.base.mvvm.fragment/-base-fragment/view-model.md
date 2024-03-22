//[presentation](../../../index.md)/[com.veles.purchase.presentation.base.mvvm.fragment](../index.md)/[BaseFragment](index.md)/[viewModel](view-model.md)

# viewModel

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

inline fun &lt;[VM](view-model.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [viewModel](view-model.md)(viewModelStoreOwner: [ViewModelStoreOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelStoreOwner.html) = checkNotNull(LocalViewModelStoreOwner.current) {
            &quot;No ViewModelStoreOwner was provided via LocalViewModelStoreOwner&quot;
        }, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, factory: [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)? = viewModelFactory): [VM](view-model.md)
