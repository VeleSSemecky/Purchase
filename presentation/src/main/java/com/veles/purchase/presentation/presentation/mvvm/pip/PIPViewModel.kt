package com.veles.purchase.presentation.presentation.mvvm.pip

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.core.loger.Logger
import com.veles.purchase.presentation.data.broadcast.RemoteActionBroadcastReceiver
import com.veles.purchase.presentation.extensions.launchOnError
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PIPViewModel @Inject constructor(
    private val logger: Logger,
    private val contentResolver: ContentResolver,
    remoteActionBroadcastReceiver: RemoteActionBroadcastReceiver
) : ViewModel() {

    val flowVideoRecordEvent = MutableStateFlow<VideoRecordEvent?>(null)
    val flowCameraIndex = MutableStateFlow(CameraSelector.LENS_FACING_BACK)
    val flowIsAudio = MutableStateFlow(false)
    val flowVideoCaptureRecorder = MutableStateFlow<VideoCapture<Recorder>?>(null)
    val flowActiveRecording = MutableStateFlow<Recording?>(null)

    val flowBroadcastReceiverRemoteAction: Flow<Intent?> =
        remoteActionBroadcastReceiver.flowBroadcastReceiver(viewModelScope)

    fun getLogger() = logger

    fun setVideoRecordEvent(value: VideoRecordEvent) = viewModelScope.launchOnError {
        flowVideoRecordEvent.emit(value)
    }

    fun setVideoCaptureRecorder(videoCapture: VideoCapture<Recorder>) =
        viewModelScope.launchOnError {
            flowVideoCaptureRecorder.emit(videoCapture)
        }

    @SuppressLint("MissingPermission")
    private fun onRecordingStart(context: Context) = viewModelScope.launchOnError {
        val name = "CameraX-recording-" +
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis()) + ".mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
        }
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
            contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        ).setContentValues(contentValues)
            .build()
        val activeRecording = (flowVideoCaptureRecorder.value ?: return@launchOnError).output
            .prepareRecording(context, mediaStoreOutput)
            .apply { if (flowIsAudio.value) withAudioEnabled() }
            .start(ContextCompat.getMainExecutor(context)) { event ->
                if (event !is VideoRecordEvent.Status) setVideoRecordEvent(event)
            }
        flowActiveRecording.emit(activeRecording)
    }

    private fun onRecordingResume() = flowActiveRecording.value?.resume()

    fun onRecordingStop() = flowActiveRecording.value?.stop()
    fun onRecordingPause() = flowActiveRecording.value?.pause()
    fun onRecordingPlay(context: Context) {
        when (flowVideoRecordEvent.value) {
            null, is VideoRecordEvent.Finalize -> onRecordingStart(context)
            else -> onRecordingResume()
        }
    }

    suspend fun onCameraSwitched() = flowCameraIndex.emit(
        when (flowCameraIndex.value) {
            CameraSelector.LENS_FACING_BACK -> CameraSelector.LENS_FACING_FRONT
            else -> CameraSelector.LENS_FACING_BACK
        }
    )
}
