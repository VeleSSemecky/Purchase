package com.veles.purchase.presentation.base.mvvm.fragment

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Base Fragment converted from Dagger to Koin
 */
abstract class BaseFragment : Fragment() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    inline fun <reified VM : ViewModel> fragmentViewModel(): VM = viewModel<VM>().value
}
