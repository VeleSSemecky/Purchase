package com.veles.purchase.presentation.presentation.mvvm.purchase.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.presentation.extensions.launchOnError
import com.veles.purchase.presentation.model.progress.Progress
import com.veles.purchase.presentation.update.AppUpdateHandler
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UpdateViewModel @Inject constructor(
    private val appUpdateHandler: AppUpdateHandler
) : ViewModel() {

    private val _flowProgress: MutableStateFlow<Progress> = MutableStateFlow(Progress.Start)
    val flowProgress: StateFlow<Progress>
        get() = _flowProgress.asStateFlow()

    fun onUpdateAvailabilityCheck(
        updateFlowResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) = viewModelScope.launchOnError {
        val isUpdateNeed = appUpdateHandler.onUpdateApp(updateFlowResultLauncher)
        val progress = when {
            isUpdateNeed -> Progress.Start
            else -> Progress.End
        }
        _flowProgress.emit(progress)
    }

    fun onResume(updateFlowResultLauncher: ActivityResultLauncher<IntentSenderRequest>) =
        viewModelScope.launchOnError {
            appUpdateHandler.onResume(updateFlowResultLauncher)
        }
}
