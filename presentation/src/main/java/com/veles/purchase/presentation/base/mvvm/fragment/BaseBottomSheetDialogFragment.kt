package com.veles.purchase.presentation.base.mvvm.fragment

import android.content.Context
import androidx.annotation.MainThread
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.veles.purchase.presentation.base.mvvm.viewmodel.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return fragmentDispatchingAndroidInjector
    }

    @MainThread
    inline fun <reified VM : ViewModel> viewModelsWithFactory(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this }
    ) = createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { ownerProducer().viewModelStore },
        factoryProducer = { viewModelFactory }
    )
}
