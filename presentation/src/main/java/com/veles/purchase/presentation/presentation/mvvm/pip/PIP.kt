package com.veles.purchase.presentation.presentation.mvvm.pip

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Rational
import androidx.activity.viewModels
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.ActiveRecording
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.veles.purchase.presentation.base.mvvm.activity.BaseActivity
import com.veles.purchase.presentation.common.Keys
import com.veles.purchase.presentation.data.video.CameraCapability
import com.veles.purchase.presentation.databinding.ActivityPipBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PIP : BaseActivity() {

    private val viewModel: PIPViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityPipBinding

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        binding = ActivityPipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flPip.setOnClickListener {
            minimize()
        }

        binding.flPip.doOnLayout {
            updatePictureInPictureParams()
        }
        viewModel.flowBroadcastReceiverRemoteAction.onEach {
            when (it?.getStringExtra(Keys.Video.REMOTE_ACTION)) {
                Keys.Video.PLAY -> remoteActionPlay()
                Keys.Video.PAUSE -> remoteActionPause()
                Keys.Video.STOP -> remoteActionStop()
                Keys.Video.SWITCH -> remoteActionSwitch()
            }
        }.launchIn(lifecycleScope)

        lifecycleScope.launch(Dispatchers.Main) {
            if (enumerationDeferred != null) {
                enumerationDeferred?.await()
                enumerationDeferred = null
            }
            bindCaptureUseCase()
        }
    }

    private fun remoteActionPause() {
        activeRecording?.pause()
        setPictureInPictureParams(
            params.value.setActions(
                arrayListOf(
                    remoteActionPlay.value,
                    remoteActionStop.value
                )
            ).build()
        )
    }

    private fun remoteActionPlay() {
        if (!this::recordingState.isInitialized || recordingState is VideoRecordEvent.Finalize) {
            startRecording()
            return
        }
        activeRecording?.resume()
        setPictureInPictureParams(
            params.value.setActions(
                arrayListOf(
                    remoteActionPause.value,
                    remoteActionStop.value
                )
            ).build()
        )
    }

    private fun remoteActionStop() {
        activeRecording?.stop()
        setPictureInPictureParams(
            params.value.setActions(
                arrayListOf(
                    remoteActionPlay.value,
                    remoteActionSwitch.value
                )
            ).build()
        )
    }

    private fun remoteActionSwitch() {
        cameraIndex =
            if (cameraCapabilities.size == cameraIndex + 1) 0 else cameraIndex + 1
        cameraSelector = CameraSelector.Builder()
            .requireLensFacing(getCameraLensFacing(cameraIndex))
            .build()
        bindCaptureUseCase()
    }

//    override fun onPictureInPictureModeChanged(
//        isInPictureInPictureMode: Boolean,
//        newConfig: Configuration
//    ) {
//        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
//    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adjustFullScreen(newConfig)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) adjustFullScreen(resources.configuration)
    }

    private fun adjustFullScreen(config: Configuration) {
//        val insetsController = ViewCompat.getWindowInsetsController(window.decorView)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        when (config.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> insetsController.hide(WindowInsetsCompat.Type.systemBars())
            else -> insetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    /**
     * Enters Picture-in-Picture mode.
     */
    private fun minimize() {
        enterPictureInPictureMode(updatePictureInPictureParams())
    }

    private val remoteActionPause = lazy {
        RemoteAction(
            Icon.createWithResource(
                this@PIP,
                com.veles.purchase.presentation.R.drawable.ic_baseline_pause_24
            ),
            Keys.Video.PAUSE,
            Keys.Video.PAUSE,
            PendingIntent.getBroadcast(
                this,
                UUID.randomUUID().hashCode(),
                Intent("G_ALARM_ACTION").apply {
                    putExtra(Keys.Video.REMOTE_ACTION, Keys.Video.PAUSE)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    private val remoteActionPlay = lazy {
        RemoteAction(
            Icon.createWithResource(
                this@PIP,
                com.veles.purchase.presentation.R.drawable.ic_baseline_play_arrow_24
            ),
            Keys.Video.PLAY,
            Keys.Video.PLAY,
            PendingIntent.getBroadcast(
                this,
                UUID.randomUUID().hashCode(),
                Intent("G_ALARM_ACTION").apply {
                    putExtra(Keys.Video.REMOTE_ACTION, Keys.Video.PLAY)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    private val remoteActionStop = lazy {
        RemoteAction(
            Icon.createWithResource(
                this@PIP,
                com.veles.purchase.presentation.R.drawable.ic_baseline_stop_24
            ),
            Keys.Video.STOP,
            Keys.Video.STOP,
            PendingIntent.getBroadcast(
                this,
                UUID.randomUUID().hashCode(),
                Intent("G_ALARM_ACTION").apply {
                    putExtra(Keys.Video.REMOTE_ACTION, Keys.Video.STOP)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    private val remoteActionSwitch = lazy {
        RemoteAction(
            Icon.createWithResource(
                this@PIP,
                com.veles.purchase.presentation.R.drawable.ic_baseline_cameraswitch_24
            ),
            Keys.Video.SWITCH,
            Keys.Video.SWITCH,
            PendingIntent.getBroadcast(
                this,
                UUID.randomUUID().hashCode(),
                Intent("G_ALARM_ACTION").apply {
                    putExtra(Keys.Video.REMOTE_ACTION, Keys.Video.SWITCH)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    private val params = lazy {
        val aspectRatio = Rational(binding.flPip.width, binding.flPip.height)
        val visibleRect = Rect()
        binding.flPip.getGlobalVisibleRect(visibleRect)
        PictureInPictureParams.Builder()
            .setAspectRatio(aspectRatio)
            .setActions(arrayListOf(remoteActionPlay.value, remoteActionSwitch.value))
            .setSourceRectHint(visibleRect)
    }

    private fun updatePictureInPictureParams(): PictureInPictureParams {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            params.value.setAutoEnterEnabled(true)
        }
        val params = params.value
            .build()
        setPictureInPictureParams(params)
        return params
    }

    private val cameraCapabilities = mutableListOf<CameraCapability>()

    private lateinit var videoCapture: VideoCapture<Recorder>
    private var activeRecording: ActiveRecording? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraSelector: CameraSelector? = null
    private lateinit var recordingState: VideoRecordEvent

    // / Camera UI inputs
    private var cameraIndex = 0
    private var qualitySelectorIndex = 0
    private var audioEnabled = false

    private val mainThreadExecutor by lazy { ContextCompat.getMainExecutor(this) }
    private var enumerationDeferred: Deferred<Unit>? = null

//    override fun onDestroy() {
//        cameraProvider?.unbindAll()
//        super.onDestroy()
//    }

    private fun bindCaptureUseCase() {
        lifecycleScope.launch {
            cameraProvider = ProcessCameraProvider.getInstance(this@PIP).await()

            cameraSelector = CameraSelector.Builder()
                .requireLensFacing(getCameraLensFacing(cameraIndex))
                .build()
            val preview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build().apply {
                    setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            // create the user required QualitySelector (video resolution): we know this is
            // supported, a valid qualitySelector will be created.
            val qualitySelector = QualitySelector.of(
                cameraCapabilities[cameraIndex].selector[qualitySelectorIndex]
            )

            // build a recorder, which can:
            //   - record video/audio to MediaStore(only use here), File, ParcelFileDescriptor
            //   - be used create recording(s) (the recording performs recording)
            val recorder = Recorder.Builder()
                .setQualitySelector(qualitySelector)
                .build()

            videoCapture = VideoCapture.withOutput(recorder)
            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this@PIP,
                    cameraSelector!!,
                    videoCapture,
                    preview
                )
            } catch (exc: Exception) {
                viewModel.getLogger().e(this@PIP::class.java.name, "Use case binding failed", exc)
                resetUIandState()
            }
        }
    }

    /**
     * Retrieve the asked camera's type(lens facing type). In this sample, only 2 types:
     *   idx is even number:  CameraSelector.LENS_FACING_BACK
     *          odd number:   CameraSelector.LENS_FACING_FRONT
     */
    private fun getCameraLensFacing(idx: Int): Int {
        if (cameraCapabilities.size == 0) {
            viewModel.getLogger().i(
                this@PIP::class.java.name,
                "Error: This device does not have any camera, bailing out"
            )
            finish()
        }
        return (cameraCapabilities[idx % cameraCapabilities.size].lensFacing)
    }

    private fun resetUIandState() {
        lifecycleScope.launch(Dispatchers.Main) {
//            enableUI(true)
//            fragmentCameraBinding.captureButton.setImageResource(R.drawable.ic_start)
//            fragmentCameraBinding.stopButton.visibility = View.INVISIBLE
//            fragmentCameraBinding.captureStatus.text = "Capture system reset due to binding failure," +
//                    "\nyou could retry with new settings."
            bindCaptureUseCase()
        }
    }

    /**
     * Kick start the video recording
     *   - config Recorder to capture to MediaStoreOutput
     *   - register RecordEvent Listener
     *   - apply audio request from user
     *   - start recording!
     * After this function, user could start/pause/resume/stop recording and application listens
     * to VideoRecordEvent for the current recording status.
     */
    @SuppressLint("MissingPermission")
    private fun startRecording() {
        setPictureInPictureParams(
            params.value.setActions(
                arrayListOf(
                    remoteActionPause.value,
                    remoteActionStop.value
                )
            )
                .build()
        )
        // create MediaStoreOutputOptions for our recorder: resulting our recording!
        val name = "CameraX-recording-" +
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
        }
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
            contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )
            .setContentValues(contentValues)
            .build()
        // configure Recorder and Start recording to the mediaStoreOutput.
        activeRecording =
            videoCapture.output.prepareRecording(this, mediaStoreOutput)
                .withEventListener(
                    mainThreadExecutor,
                    captureListener
                )
                .apply { if (audioEnabled) withAudioEnabled() }
                .start()
    }

    private val captureListener = Consumer<VideoRecordEvent> { event ->
        // cache the recording state
        if (event !is VideoRecordEvent.Status) {
            recordingState = event
        }

//        updateUI(event)

        if (event is VideoRecordEvent.Finalize) {
//            showVideo(event)
        }
    }

    init {
        enumerationDeferred = lifecycleScope.async {
            whenCreated {
                val provider = ProcessCameraProvider.getInstance(this@PIP).await()

                provider.unbindAll()
                for (lens in arrayOf(
                    CameraSelector.LENS_FACING_BACK,
                    CameraSelector.LENS_FACING_FRONT
                )) {
                    val cameraSelector = CameraSelector.Builder().requireLensFacing(lens).build()
                    try {
                        // just want to get the camera.cameraInfo to query capabilities
                        // we are not binding anything here.
                        if (provider.hasCamera(cameraSelector)) {
                            val camera = provider.bindToLifecycle(this@PIP, cameraSelector)
                            val qualityCap =
                                QualitySelector.getSupportedQualities(camera.cameraInfo)
                                    .filter { quality ->
                                        listOf(
                                            QualitySelector.QUALITY_UHD,
                                            QualitySelector.QUALITY_FHD,
                                            QualitySelector.QUALITY_HD
                                        ).contains(quality)
                                    }
                            cameraCapabilities.add(CameraCapability(lens, qualityCap))
                        }
                    } catch (exc: java.lang.Exception) {
                        Firebase.crashlytics.recordException(exc)
                    }
                }
            }
        }
    }

    companion object {
        // default QualitySelector if no input from UI
        const val DEFAULT_QUALITY_SELECTOR_IDX = 0
        const val DEFAULT_ASPECT_RATIO = AspectRatio.RATIO_16_9
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
