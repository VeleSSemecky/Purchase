//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.purchase.navigation](../index.md)/[UpdateViewModel](index.md)

# UpdateViewModel

[androidJvm]\
class [UpdateViewModel](index.md)@Injectconstructor(appUpdateHandler: [AppUpdateHandler](../../com.veles.purchase.presentation.update/-app-update-handler/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [UpdateViewModel](-update-view-model.md) | [androidJvm]<br>@Inject<br>constructor(appUpdateHandler: [AppUpdateHandler](../../com.veles.purchase.presentation.update/-app-update-handler/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowProgress](flow-progress.md) | [androidJvm]<br>val [flowProgress](flow-progress.md): StateFlow&lt;[Progress](../../com.veles.purchase.presentation.model.progress/-progress/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [onResume](on-resume.md) | [androidJvm]<br>fun [onResume](on-resume.md)(updateFlowResultLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)&lt;[IntentSenderRequest](https://developer.android.com/reference/kotlin/androidx/activity/result/IntentSenderRequest.html)&gt;): Job |
| [onUpdateAvailabilityCheck](on-update-availability-check.md) | [androidJvm]<br>fun [onUpdateAvailabilityCheck](on-update-availability-check.md)(updateFlowResultLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)&lt;[IntentSenderRequest](https://developer.android.com/reference/kotlin/androidx/activity/result/IntentSenderRequest.html)&gt;): Job |
