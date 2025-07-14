package com.veles.purchase.presentation.presentation.mvvm.purchase.photo

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.navigation.fragment.navArgs

/**
 * Koin module for PhotoPurchaseCompose feature
 *
 * BEFORE (Dagger):
 * @Module
 * interface PhotoPurchaseComposeModule {
 *     @Binds
 *     @IntoMap
 *     @ViewModelKey(PhotoPurchaseComposeViewModel::class)
 *     fun bindViewModel(viewModel: PhotoPurchaseComposeViewModel): ViewModel
 *
 *     companion object {
 *         @Provides
 *         fun provideArgs(fragment: PhotoPurchaseComposeFragment): PhotoPurchaseComposeFragmentArgs {
 *             return fragment.navArgs<PhotoPurchaseComposeFragmentArgs>().value
 *         }
 *     }
 * }
 *
 * AFTER (Koin):
 * DSL-based module with factory for arguments
 */
val photoPurchaseComposeKoinModule = module {

    viewModel {
        PhotoPurchaseComposeViewModel(
            savedStateHandle = get(),
            router = get(),
            sharedFlowBus = get(),
            getPhotoUseCase = get()
        )
    }
}
