package com.veles.purchase.presentation.presentation.mvvm.purchase.collection

import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.EditCollectionComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category.CategoryViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category.categoryModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.editCollectionComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.CollectionPurchaseComposeViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.collectionPurchaseComposeModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for Collection-related features
 * Consolidates collection management modules
 *
 * BEFORE (Dagger):
 * Separate modules: CollectionPurchaseComposeModule, EditCollectionComposeModule, CategoryModule
 *
 * AFTER (Koin):
 * Single consolidated module with logical grouping
 */
val collectionKoinModule = module {




}
