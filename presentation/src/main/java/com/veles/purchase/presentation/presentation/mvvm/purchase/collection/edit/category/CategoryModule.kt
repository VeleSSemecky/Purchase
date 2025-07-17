package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category

import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.CollectionPurchaseComposeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//
//import androidx.lifecycle.ViewModel
//import androidx.navigation.fragment.navArgs
//import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.multibindings.IntoMap
//
//@Module
//interface CategoryModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CategoryViewModel::class)
//    fun bindViewModel(viewModel: CategoryViewModel): ViewModel
//
//    companion object {
//
//        @Provides
//        fun provideArgs(fragment: CategoryFragment): CategoryFragmentArgs {
//            return fragment.navArgs<CategoryFragmentArgs>().value
//        }
//    }
//}

val categoryModule = module {

    // Category ViewModel
    viewModel {
        CategoryViewModel(
            savedStateHandle = get(),
            savePurchaseCategoryUseCase = get(),
            router = get(),
        )
    }
}
