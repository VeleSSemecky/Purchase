package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.di.annotation.scope.FragmentScope
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose.CurrencyChooseFragment
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.choose.CurrencyChooseModule
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search.CurrencySearchFragment
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.currency.search.CurrencySearchModule
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.month.MonthChooseFragment
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.month.MonthChooseModule
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.year.YearChooseFragment
import com.veles.purchase.presentation.presentation.compose.shopping.dialog.date.year.YearChooseModule
import com.veles.purchase.presentation.presentation.compose.shopping.edit.SkuEditFragment
import com.veles.purchase.presentation.presentation.compose.shopping.edit.SkuEditModule
import com.veles.purchase.presentation.presentation.compose.shopping.graph.OutlayGraphFragment
import com.veles.purchase.presentation.presentation.compose.shopping.graph.OutlayGraphModule
import com.veles.purchase.presentation.presentation.compose.shopping.list.SkuListFragment
import com.veles.purchase.presentation.presentation.compose.shopping.list.SkuListModule
import com.veles.purchase.presentation.presentation.compose.shopping.photo.PhotoListFragment
import com.veles.purchase.presentation.presentation.compose.shopping.photo.PhotoListModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.biometric.BiometricComposeFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.biometric.BiometricComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.EditCollectionComposeFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.edit.EditCollectionComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.CollectionPurchaseComposeFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.collection.list.CollectionPurchaseComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.edit.EditPurchaseFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.edit.EditPurchaseModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.history.HistoryComposeFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.history.HistoryComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.list.ListPurchaseFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.list.ListPurchaseModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.login.LoginFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.login.LoginModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.navigation.NavigationFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.navigation.NavigationModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.PhotoPurchaseComposeFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.PhotoPurchaseComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.setting.SettingPurchaseComposeFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.setting.SettingPurchaseComposeModule
import com.veles.purchase.presentation.presentation.mvvm.purchase.sort.SortPurchaseFragment
import com.veles.purchase.presentation.presentation.mvvm.purchase.sort.SortPurchaseModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsContributorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ListPurchaseModule::class])
    fun provideListPurchaseComposeFragment(): ListPurchaseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [EditPurchaseModule::class])
    fun provideAddedPurchaseCompose(): EditPurchaseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SortPurchaseModule::class])
    fun provideSortPurchase(): SortPurchaseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [EditCollectionComposeModule::class])
    fun provideCreateCollectionCompose(): EditCollectionComposeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    fun provideLogin(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [NavigationModule::class])
    fun provideNavigation(): NavigationFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SkuEditModule::class])
    fun provideSkuEdit(): SkuEditFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SkuListModule::class])
    fun provideSkuList(): SkuListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [OutlayGraphModule::class])
    fun provideOutlayGraph(): OutlayGraphFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [YearChooseModule::class])
    fun provideYearChoose(): YearChooseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MonthChooseModule::class])
    fun provideMonthChoose(): MonthChooseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CurrencyChooseModule::class])
    fun provideCurrencyChoose(): CurrencyChooseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CurrencySearchModule::class])
    fun provideCurrencySearch(): CurrencySearchFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [PhotoListModule::class])
    fun providePhotoList(): PhotoListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [PhotoPurchaseComposeModule::class])
    fun providePhotoPurchaseCompose(): PhotoPurchaseComposeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CollectionPurchaseComposeModule::class])
    fun provideCollectionPurchaseCompose(): CollectionPurchaseComposeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HistoryComposeModule::class])
    fun provideHistoryCompose(): HistoryComposeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingPurchaseComposeModule::class])
    fun provideSettingPurchaseCompose(): SettingPurchaseComposeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [BiometricComposeModule::class])
    fun provideBiometricCompose(): BiometricComposeFragment
}
