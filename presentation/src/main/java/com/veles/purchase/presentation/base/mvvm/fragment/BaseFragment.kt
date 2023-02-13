package com.veles.purchase.presentation.base.mvvm.fragment

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.veles.purchase.presentation.base.mvvm.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    inline fun <reified VM : ViewModel> viewModel(
        viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
        key: String? = null,
        factory: ViewModelProvider.Factory? = viewModelFactory
    ): VM = androidx.lifecycle.viewmodel.compose.viewModel(
        VM::class.java,
        viewModelStoreOwner,
        key,
        factory
    )
}
