package com.veles.purchase.presentation.di.component

import android.content.Context
import com.veles.purchase.presentation.AppApplication
import com.veles.purchase.presentation.di.module.ActivitiesContributorModule
import com.veles.purchase.presentation.di.module.BusModule
import com.veles.purchase.presentation.di.module.CoroutineDispatcherModule
import com.veles.purchase.presentation.di.module.CryptographyModule
import com.veles.purchase.presentation.di.module.DataBaseModule
import com.veles.purchase.presentation.di.module.DataStoreModule
import com.veles.purchase.presentation.di.module.LoggerModule
import com.veles.purchase.presentation.di.module.NetworkModule
import com.veles.purchase.presentation.di.module.NotificationModule
import com.veles.purchase.presentation.di.module.PersistenceModule
import com.veles.purchase.presentation.di.module.RepositoryModule
import com.veles.purchase.presentation.di.module.ViewModelProviderFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelProviderFactoryModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DataStoreModule::class,
        NotificationModule::class,
        DataBaseModule::class,
        BusModule::class,
        CoroutineDispatcherModule::class,
        LoggerModule::class,
        ActivitiesContributorModule::class,
        PersistenceModule::class,
        CryptographyModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
