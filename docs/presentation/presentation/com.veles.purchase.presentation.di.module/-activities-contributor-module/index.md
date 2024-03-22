//[presentation](../../../index.md)/[com.veles.purchase.presentation.di.module](../index.md)/[ActivitiesContributorModule](index.md)

# ActivitiesContributorModule

[androidJvm]\
@Module

interface [ActivitiesContributorModule](index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [mainActivity](main-activity.md) | [androidJvm]<br>@ContributesAndroidInjector(modules = [[FragmentsContributorModule::class](../-fragments-contributor-module/index.md), [NavControllerModule::class](../-nav-controller-module/index.md)])<br>abstract fun [mainActivity](main-activity.md)(): [MainActivity](../../com.veles.purchase.presentation.presentation.activity/-main-activity/index.md) |
| [pipActivity](pip-activity.md) | [androidJvm]<br>@[PIPScope](../../com.veles.purchase.presentation.presentation.mvvm.pip/-p-i-p-scope/index.md)<br>@ContributesAndroidInjector(modules = [[PIPModule::class](../../com.veles.purchase.presentation.presentation.mvvm.pip/-p-i-p-module/index.md)])<br>abstract fun [pipActivity](pip-activity.md)(): [PIP](../../com.veles.purchase.presentation.presentation.mvvm.pip/-p-i-p/index.md) |
