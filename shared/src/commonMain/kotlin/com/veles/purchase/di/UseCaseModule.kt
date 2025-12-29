package com.veles.purchase.di

import com.veles.purchase.domain.usecase.collection.DeletePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseCategoryUseCase
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.collection.SavePurchaseCategoryUseCase
import com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.price.PriceUseCase
import com.veles.purchase.domain.usecase.purchase.AddLazyPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.CheckPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.DeletePurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.FirebasePurchaseSendUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchaseHistoryUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchasesUseCase
import com.veles.purchase.domain.usecase.purchase.MoveForLaterPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.SetPurchaseHistoryUseCase
import com.veles.purchase.domain.usecase.setting.GetSettingUseCase
import com.veles.purchase.domain.usecase.setting.SetSettingUseCase
import com.veles.purchase.domain.usecase.sku.DeleteSkuPhotoUseCase
import com.veles.purchase.domain.usecase.sku.DeleteSkuUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuPhotoUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuSumMontUseCase
import com.veles.purchase.domain.usecase.sku.GetSkuUseCase
import com.veles.purchase.domain.usecase.sku.SetSkuUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import org.koin.dsl.module

/**
 * UseCase Module - provides all use cases for dependency injection
 * Phase 6 Complete: All UseCases migrated from domain module
 */
val useCaseModule = module {

    // SKU UseCases
    single { GetSkuUseCase(skuRepository = get()) }
    single { SetSkuUseCase(skuRepository = get()) }
    single { DeleteSkuUseCase(skuRepository = get()) }
    single { GetSkuSumMontUseCase(skuRepository = get()) }
    single { GetSkuPhotoUseCase(skuPhotoRepository = get()) }
    single { DeleteSkuPhotoUseCase(skuPhotoRepository = get()) }

    // Purchase UseCases
    single { GetPurchasesUseCase(purchaseRepository = get()) }
    single { GetPurchaseUseCase(purchaseRepository = get()) }
    single { SavePurchaseUseCase(purchaseRepository = get()) }
    single { DeletePurchaseUseCase(purchaseRepository = get(), historyRepository = get()) }
    single { CheckPurchaseUseCase(purchaseRepository = get()) }
    single { AddLazyPurchaseUseCase(purchaseRepository = get(), historyRepository = get()) }
    single { MoveForLaterPurchaseUseCase(purchaseRepository = get()) }
    single { FirebasePurchaseSendUseCase(purchaseRepository = get()) }

    // Collection UseCases
    single { GetCollectionPurchaseUseCase(collectionRepository = get()) }
    single { FirebaseFirestorePurchaseCollectionUseCase(collectionRepository = get()) }
    single { DeletePurchaseCollectionUseCase(collectionRepository = get()) }
    single { SetCollectionPurchaseUseCase(collectionRepository = get()) }
    single { SavePurchaseCategoryUseCase(collectionRepository = get()) }
    single { GetCollectionPurchaseCategoryUseCase(collectionRepository = get()) }

    // History UseCases
    single { GetPurchaseHistoryUseCase(historyRepository = get()) }
    single { SetPurchaseHistoryUseCase(historyRepository = get()) }

    // Settings UseCases
    single { GetSettingUseCase(settingRepository = get()) }
    single { SetSettingUseCase(settingRepository = get()) }

    // User UseCases
    single { UserUseCase(firebaseGetUserRepository = get()) }

    // Price UseCase
    single { PriceUseCase() }
}

