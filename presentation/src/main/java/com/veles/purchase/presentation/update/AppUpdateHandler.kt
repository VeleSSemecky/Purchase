package com.veles.purchase.presentation.update

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.veles.purchase.presentation.presentation.activity.MainActivity
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class AppUpdateHandler @Inject constructor(private val activity: MainActivity) {

    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(activity) }

    suspend fun onUpdateApp(
        updateFlowResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ): Boolean = try {
        val appUpdateInfo = appUpdateManager.appUpdateInfo.await()
        when {
            appUpdateInfo.isUpdateAvailableOrImmediate() -> {
                startUpdateFlowForResult(updateFlowResultLauncher, appUpdateInfo)
                true
            }

            else -> false
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        false
    }

    suspend fun onResume(updateFlowResultLauncher: ActivityResultLauncher<IntentSenderRequest>) {
        val appUpdateInfo = appUpdateManager.appUpdateInfo.await()
        if (appUpdateInfo.updateAvailability() != UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) return
        startUpdateFlowForResult(updateFlowResultLauncher, appUpdateInfo)
    }

    private fun startUpdateFlowForResult(
        updateFlowResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
        appUpdateInfo: AppUpdateInfo
    ) {
        val starter = IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
            val request = IntentSenderRequest.Builder(intent)
                .setFillInIntent(fillInIntent)
                .setFlags(flagsValues, flagsMask)
                .build()

            updateFlowResultLauncher.launch(request)
        }
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            starter,
            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
            REQUEST_CODE_UPDATE
        )
    }

    private fun AppUpdateInfo.isUpdateAvailableOrImmediate() =
        updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)

    companion object {
        const val REQUEST_CODE_UPDATE = 32367
    }
}
