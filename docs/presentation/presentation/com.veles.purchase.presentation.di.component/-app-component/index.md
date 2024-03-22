//[presentation](../../../index.md)/[com.veles.purchase.presentation.di.component](../index.md)/[AppComponent](index.md)

# AppComponent

[androidJvm]\
@Singleton

@Component(modules = [AndroidSupportInjectionModule::class, [ViewModelProviderFactoryModule::class](../../com.veles.purchase.presentation.di.module/-view-model-provider-factory-module/index.md), [NetworkModule::class](../../com.veles.purchase.presentation.di.module/-network-module/index.md), [RepositoryModule::class](../../com.veles.purchase.presentation.di.module/-repository-module/index.md), [DataStoreModule::class](../../com.veles.purchase.presentation.di.module/-data-store-module/index.md), [NotificationModule::class](../../com.veles.purchase.presentation.di.module/-notification-module/index.md), [DataBaseModule::class](../../com.veles.purchase.presentation.di.module/-data-base-module/index.md), [BusModule::class](../../com.veles.purchase.presentation.di.module/-bus-module/index.md), [CoroutineDispatcherModule::class](../../com.veles.purchase.presentation.di.module/-coroutine-dispatcher-module/index.md), [LoggerModule::class](../../com.veles.purchase.presentation.di.module/-logger-module/index.md), [ActivitiesContributorModule::class](../../com.veles.purchase.presentation.di.module/-activities-contributor-module/index.md), [PersistenceModule::class](../../com.veles.purchase.presentation.di.module/-persistence-module/index.md), [CryptographyModule::class](../../com.veles.purchase.presentation.di.module/-cryptography-module/index.md)])

interface [AppComponent](index.md) : AndroidInjector&lt;[AppApplication](../../com.veles.purchase.presentation/-app-application/index.md)&gt;

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>@Component.Builder<br>interface [Builder](-builder/index.md) |

## Functions

| Name | Summary |
|---|---|
| [inject](index.md#-1323923498%2FFunctions%2F-646359276) | [androidJvm]<br>abstract fun [inject](index.md#-1323923498%2FFunctions%2F-646359276)(p0: [AppApplication](../../com.veles.purchase.presentation/-app-application/index.md)) |
