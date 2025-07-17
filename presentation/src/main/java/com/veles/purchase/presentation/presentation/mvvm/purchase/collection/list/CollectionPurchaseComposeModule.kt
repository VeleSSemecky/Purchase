package com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//
//import androidx.lifecycle.ViewModel
//import com.veles.purchase.presentation.di.annotation.mapkey.ViewModelKey
//import dagger.Binds
//import dagger.Module
//import dagger.multibindings.IntoMap
//
//@Module
//interface CollectionPurchaseComposeModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CollectionPurchaseComposeViewModel::class)
//    fun bindViewModel(viewModel: CollectionPurchaseComposeViewModel): ViewModel
//}
val collectionPurchaseComposeModule = module {

// CollectionPurchaseCompose ViewModel
    viewModel {
        CollectionPurchaseComposeViewModel(
            firebaseFirestorePurchaseCollectionUseCase = get(),
            deletePurchaseCollectionUseCase = get(),
        )
    }
}
