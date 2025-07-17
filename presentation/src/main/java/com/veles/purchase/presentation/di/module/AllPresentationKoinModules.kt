package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.presentation.compose.shopping.list.skuListModule
import org.koin.dsl.module
import com.veles.purchase.presentation.presentation.mvvm.purchase.login.loginKoinModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.photoPurchaseComposeKoinModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.navigation.navigationKoinModule
import com.veles.purchase.presentation.presentation.compose.shopping.shoppingKoinModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.purchaseKoinModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.collectionKoinModule
import com.veles.purchase.presentation.presentation.mvvm.pip.pipKoinModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.category.categoryModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.editCollectionComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.collectionPurchaseComposeModule

/**
 * Main Koin module that consolidates all feature modules
 *
 * BEFORE (Dagger):
 * @Component(modules = [
 *     AppModule::class,
 *     LoginModule::class,
 *     PhotoPurchaseComposeModule::class,
 *     NavigationModule::class,
 *     // ... інші модулі
 * ])
 * interface AppComponent
 *
 * AFTER (Koin):
 * Simple list of modules for startKoin()
 */
val allPresentationKoinModules = listOf(
    // Core infrastructure modules
    loggerModule,
    coroutineDispatcherModule,
    busModule,
    persistenceModule,
    dataStoreModule,
    cryptographyModule,
    networkModule,

    // Database modules
    databaseModule,
    daoModule,

    // Repository modules
    repositoryModule,

    // UI modules
    androidComponentModule,
    uiModule,
    viewModelModule,
    navControllerModule,
    notificationModule,

    // Feature modules
    loginKoinModule,
    navigationKoinModule,
    purchaseKoinModule,
    photoPurchaseComposeKoinModule,
    editCollectionComposeModule,
    collectionPurchaseComposeModule,
    categoryModule,
    shoppingKoinModule,
    skuListModule,
    pipKoinModule,

    // Data layer integration

    // Domain layer integration
    useCaseModule
)

/**
 * Single consolidated module (alternative approach)
 * If you prefer one big module instead of separate ones
 */
val consolidatedPresentationModule = module {
    includes(
        loggerModule,
        coroutineDispatcherModule,
        busModule,
        persistenceModule,
        dataStoreModule,
        cryptographyModule,
        networkModule,
        databaseModule,
        daoModule,
        repositoryModule,
        uiModule,
        viewModelModule,
        navControllerModule,
        notificationModule,
        loginKoinModule,
        navigationKoinModule,
        purchaseKoinModule,
        photoPurchaseComposeKoinModule,
        editCollectionComposeModule,
        collectionPurchaseComposeModule,
        categoryModule,
        shoppingKoinModule,
        skuListModule,
        pipKoinModule,
        useCaseModule // Added missing useCaseModule
    )
}
