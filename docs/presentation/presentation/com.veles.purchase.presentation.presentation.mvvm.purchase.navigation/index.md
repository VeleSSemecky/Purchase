//[presentation](../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.navigation](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [NavigationFragment](-navigation-fragment/index.md) | [androidJvm]<br>class [NavigationFragment](-navigation-fragment/index.md) : [BaseFragment](../com.veles.purchase.presentation.base.mvvm.fragment/-base-fragment/index.md), [MenuItemSelected](../com.veles.purchase.presentation.base.mvvm.fragment/-menu-item-selected/index.md) |
| [NavigationModule](-navigation-module/index.md) | [androidJvm]<br>@Module<br>interface [NavigationModule](-navigation-module/index.md) |
| [NavigationViewModel](-navigation-view-model/index.md) | [androidJvm]<br>class [NavigationViewModel](-navigation-view-model/index.md)@Injectconstructor(userUseCase: [UserUseCase](../../../domain/domain/com.veles.purchase.domain.usecase.user/-user-use-case/index.md), logoutUseCase: [LogoutUseCase](../../../domain/domain/com.veles.purchase.domain.usecase.logout/-logout-use-case/index.md), router: [Router](../com.veles.purchase.presentation.base.mvvm.navigation/-router/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html) |
| [UpdateViewModel](-update-view-model/index.md) | [androidJvm]<br>class [UpdateViewModel](-update-view-model/index.md)@Injectconstructor(appUpdateHandler: [AppUpdateHandler](../com.veles.purchase.presentation.update/-app-update-handler/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html) |
