package com.veles.purchase.presentation.presentation.mvvm.pip

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.viewModels
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.Recorder.DEFAULT_QUALITY_SELECTOR
import androidx.camera.video.VideoCapture
import androidx.concurrent.futures.await
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.lifecycle.lifecycleScope
import com.veles.purchase.presentation.base.mvvm.activity.BaseActivity
import com.veles.purchase.presentation.common.Keys
import com.veles.purchase.presentation.databinding.ActivityPipBinding
import com.veles.purchase.presentation.extensions.setPictureInPictureParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PIP : BaseActivity() {

    private val viewModel: PIPViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityPipBinding

    private val remoteActionPause by lazy { PauseRemoteAction(this) }

    private val remoteActionPlay by lazy { PlayRemoteAction(this) }

    private val remoteActionStop by lazy { StopRemoteAction(this) }

    private val remoteActionSwitch by lazy { SwitchRemoteAction(this) }

    private val params: PictureInPictureParams.Builder by lazy {
        val aspectRatio = Rational(binding.flPip.width, binding.flPip.height)
        val visibleRect = Rect()
        binding.flPip.getGlobalVisibleRect(visibleRect)
        PictureInPictureParams.Builder()
            .setAspectRatio(aspectRatio)
            .setActions(arrayListOf(remoteActionPlay.action, remoteActionSwitch.action))
            .setSourceRectHint(visibleRect)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        binding = ActivityPipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flPip.setOnClickListener {
            enterPictureInPictureMode(updatePictureInPictureParams())
        }

        binding.flPip.doOnLayout {
            updatePictureInPictureParams()
        }
        viewModel.flowBroadcastReceiverRemoteAction.filterNotNull().onEach {
            val value = it.getStringExtra(Keys.Video.REMOTE_ACTION) ?: return@onEach
            when (VideoControl.valueOf(value)) {
                VideoControl.PLAY -> remoteActionPlay()
                VideoControl.PAUSE -> remoteActionPause()
                VideoControl.STOP -> remoteActionStop()
                VideoControl.SWITCH -> remoteActionSwitch()
            }
        }.launchIn(lifecycleScope)

        lifecycleScope.launch(Dispatchers.Main) {
            bindCaptureUseCase()
        }
    }

    private fun remoteActionPause() {
        viewModel.onRecordingPause()
        setPictureInPictureParams(params, remoteActionPlay, remoteActionStop)
    }

    private fun remoteActionPlay() {
        viewModel.onRecordingPlay(this)
        setPictureInPictureParams(params, remoteActionPause, remoteActionStop)
    }

    private fun remoteActionStop() {
        setPictureInPictureParams(params, remoteActionPlay, remoteActionSwitch)
        viewModel.onRecordingStop()
    }

    private suspend fun remoteActionSwitch() {
        viewModel.onCameraSwitched()
        bindCaptureUseCase()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adjustFullScreen(newConfig)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) adjustFullScreen(resources.configuration)
    }

    private fun adjustFullScreen(config: Configuration) {
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        when (config.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> insetsController.hide(
                WindowInsetsCompat.Type.systemBars()
            )
            else -> insetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    private fun updatePictureInPictureParams(): PictureInPictureParams {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            params.setAutoEnterEnabled(true)
        }
        val params = params.build()
        setPictureInPictureParams(params)
        return params
    }

    private suspend fun bindCaptureUseCase() {
        val cameraProvider = ProcessCameraProvider.getInstance(this).await()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(viewModel.flowCameraIndex.value)
            .build()

        val recorder = Recorder.Builder()
            .setQualitySelector(DEFAULT_QUALITY_SELECTOR)
            .build()

        val videoCapture = VideoCapture.withOutput(recorder)
        viewModel.setVideoCaptureRecorder(videoCapture)
        val preview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
        preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            videoCapture,
            preview
        )
    }
}
