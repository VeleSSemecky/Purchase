package com.veles.purchase.presentation.base.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModelFactory is no longer needed with Koin
 * This class is deprecated and should be removed after migration
 * Use Koin's viewModel() delegate instead
 */
@Deprecated(
    message = "ViewModelFactory is no longer needed with Koin. Use viewModel() delegate instead.",
    replaceWith = ReplaceWith("viewModel()", "org.koin.androidx.viewmodel.ext.android.viewModel")
)
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        throw IllegalStateException("ViewModelFactory is deprecated. Use Koin's viewModel() delegate instead.")
    }
}
