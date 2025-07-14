package com.veles.purchase.presentation.di.module

import com.veles.purchase.domain.usecase.NotificationMessageUseCase
import com.veles.purchase.domain.usecase.auth.LoginUseCase
import com.veles.purchase.domain.usecase.biometric.DecryptionUseCase
import com.veles.purchase.domain.usecase.biometric.EncryptionUseCase
import com.veles.purchase.domain.usecase.collection.DeletePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.FirebaseFirestorePurchaseCollectionUseCase
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseCategoryUseCase
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.collection.SavePurchaseCategoryUseCase
import com.veles.purchase.domain.usecase.collection.SetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.logout.LogoutUseCase
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
import com.veles.purchase.domain.usecase.storage.FirebaseStorageUseCase
import com.veles.purchase.domain.usecase.storage.GetPhotoUseCase
import com.veles.purchase.domain.usecase.storage.StorageDeleteUseCase
import com.veles.purchase.domain.usecase.user.UserUseCase
import org.koin.dsl.module

val useCaseModule = module {

    // User UseCase
    factory<UserUseCase> { UserUseCase(get()) }

    // Auth UseCases
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }

    // Settings UseCases
    factory<GetSettingUseCase> { GetSettingUseCase(get()) }
    factory<SetSettingUseCase> { SetSettingUseCase(get()) }

    // Purchase UseCases
    factory {
        AddLazyPurchaseUseCase(
            appCoroutineDispatcher = get(),
            purchaseRepository = get(),
            notificationMessageRepository = get(),
            historyRepository = get(),
        )
    }
    factory {
        CheckPurchaseUseCase(
            coroutineDispatcher = get(),
            setPurchaseHistoryUseCase = get(),
            firebasePurchaseSendUseCase = get(),
        )
    }
    factory {
        DeletePurchaseUseCase(
            purchaseRepository = get(),
            deletePurchasePhotoRepository = get(),
            historyRepository = get(),
        )
    }
    factory { FirebasePurchaseSendUseCase(get()) }
    factory { GetPurchaseHistoryUseCase(get()) }
    factory { GetPurchaseUseCase(get()) }
    factory { GetPurchasesUseCase(get()) }
    factory {
        MoveForLaterPurchaseUseCase(
            purchaseLaterRepository = get(),
            purchaseRepository = get(),
        )
    }
    factory {
        SavePurchaseUseCase(
            coroutineDispatcher = get(),
            setPurchaseHistoryUseCase = get(),
            storageDeleteUseCase = get(),
            firebaseStorageUseCase = get(),
            firebasePurchaseSendUseCase = get(),
            notificationMessageUseCase = get(),
            priceUseCase = get(),
        )
    }
    factory { SetPurchaseHistoryUseCase(get()) }

    // Collection UseCases
    factory { FirebaseFirestorePurchaseCollectionUseCase(get()) }
    factory { DeletePurchaseCollectionUseCase(get()) }
    factory { GetCollectionPurchaseUseCase(get()) }
    factory {
        GetCollectionPurchaseCategoryUseCase(
            purchaseCategoryRepository = get()
        )
    }
    factory { SavePurchaseCategoryUseCase(get()) }
    factory { SetCollectionPurchaseUseCase(get()) }

    // Storage UseCases
    factory { GetPhotoUseCase(get()) }
    factory { StorageDeleteUseCase(get()) }
    factory {
        FirebaseStorageUseCase(
            setPurchasePhotoRepository = get()
        )
    }

    // Notification UseCase
    factory {
        NotificationMessageUseCase(
            notificationMessageRepository = get()
        )
    }

    // Price UseCase
    factory { PriceUseCase() }

    // SKU UseCases
    factory<DeleteSkuPhotoUseCase> { DeleteSkuPhotoUseCase(get()) }
    factory<DeleteSkuUseCase> { DeleteSkuUseCase(get()) }
    factory<GetSkuPhotoUseCase> { GetSkuPhotoUseCase(get()) }
    factory<GetSkuSumMontUseCase> { GetSkuSumMontUseCase(get()) }
    factory<GetSkuUseCase> { GetSkuUseCase(get()) }
    factory<SetSkuUseCase> { SetSkuUseCase(get()) }

    // Biometric UseCases
    factory<DecryptionUseCase> { DecryptionUseCase(get()) }
    factory<EncryptionUseCase> { EncryptionUseCase(get()) }
}
