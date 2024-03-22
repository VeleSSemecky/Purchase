//[presentation](../../../index.md)/[com.veles.purchase.presentation.di.module](../index.md)/[DataBaseModule](index.md)

# DataBaseModule

[androidJvm]\
@Module(includes = [[DaoModule::class](../-dao-module/index.md)])

object [DataBaseModule](index.md)

## Functions

| Name | Summary |
|---|---|
| [provideAppDatabase](provide-app-database.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideAppDatabase](provide-app-database.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), logger: [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md)): [AppDatabase](../../../../data/data/com.veles.purchase.data.room/-app-database/index.md) |
