package com.veles.purchase.presentation.presentation.compose.shopping.list

import com.veles.purchase.presentation.presentation.compose.shopping.photo.PhotoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

//
//import androidx.lifecycle.ViewModel
//import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
//import dagger.Binds
//import dagger.Module
//import dagger.multibindings.IntoMap
//
//@Module
//interface SkuListModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SkuListViewModel::class)
//    fun bindViewModel(viewModel: SkuListViewModel): ViewModel
//}

val skuListModule = module {

    // SkuListViewModel
    viewModel {
        SkuListViewModel(
            deleteSkuUseCase = get(),
            getSkuUseCase = get(),
            sharedFlowBus = get()
        )
    }
}
