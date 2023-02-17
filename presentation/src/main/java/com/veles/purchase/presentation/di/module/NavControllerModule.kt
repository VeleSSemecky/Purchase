package com.veles.purchase.presentation.di.module

import com.veles.purchase.presentation.base.mvvm.navigation.DaggerNavHostFragment
import com.veles.purchase.presentation.base.mvvm.navigation.Router
import com.veles.purchase.presentation.base.mvvm.navigation.RouterImpl
import com.veles.purchase.presentation.di.annotation.scope.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface NavControllerModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideDaggerNavHostFragment(): DaggerNavHostFragment

    @Binds
    fun bindRouter(logger: RouterImpl): Router
}
