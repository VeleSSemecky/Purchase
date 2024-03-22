//[presentation](../../../index.md)/[com.veles.purchase.presentation.presentation.mvvm.pip](../index.md)/[PIPViewModel](index.md)

# PIPViewModel

[androidJvm]\
class [PIPViewModel](index.md)@Injectconstructor(logger: [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md), contentResolver: [ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html), remoteActionBroadcastReceiver: [RemoteActionBroadcastReceiver](../../com.veles.purchase.presentation.data.broadcast/-remote-action-broadcast-receiver/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [PIPViewModel](-p-i-p-view-model.md) | [androidJvm]<br>@Inject<br>constructor(logger: [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md), contentResolver: [ContentResolver](https://developer.android.com/reference/kotlin/android/content/ContentResolver.html), remoteActionBroadcastReceiver: [RemoteActionBroadcastReceiver](../../com.veles.purchase.presentation.data.broadcast/-remote-action-broadcast-receiver/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [flowActiveRecording](flow-active-recording.md) | [androidJvm]<br>val [flowActiveRecording](flow-active-recording.md): MutableStateFlow&lt;[Recording](https://developer.android.com/reference/kotlin/androidx/camera/video/Recording.html)?&gt; |
| [flowBroadcastReceiverRemoteAction](flow-broadcast-receiver-remote-action.md) | [androidJvm]<br>val [flowBroadcastReceiverRemoteAction](flow-broadcast-receiver-remote-action.md): Flow&lt;[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)?&gt; |
| [flowCameraIndex](flow-camera-index.md) | [androidJvm]<br>val [flowCameraIndex](flow-camera-index.md): MutableStateFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [flowIsAudio](flow-is-audio.md) | [androidJvm]<br>val [flowIsAudio](flow-is-audio.md): MutableStateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [flowVideoCaptureRecorder](flow-video-capture-recorder.md) | [androidJvm]<br>val [flowVideoCaptureRecorder](flow-video-capture-recorder.md): MutableStateFlow&lt;[VideoCapture](https://developer.android.com/reference/kotlin/androidx/camera/video/VideoCapture.html)&lt;[Recorder](https://developer.android.com/reference/kotlin/androidx/camera/video/Recorder.html)&gt;?&gt; |
| [flowVideoRecordEvent](flow-video-record-event.md) | [androidJvm]<br>val [flowVideoRecordEvent](flow-video-record-event.md): MutableStateFlow&lt;[VideoRecordEvent](https://developer.android.com/reference/kotlin/androidx/camera/video/VideoRecordEvent.html)?&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276) | [androidJvm]<br>open fun [addCloseable](../../com.veles.purchase.presentation.presentation.mvvm.purchase.sort/-sort-purchase-view-model/index.md#264516373%2FFunctions%2F-646359276)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [getLogger](get-logger.md) | [androidJvm]<br>fun [getLogger](get-logger.md)(): [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md) |
| [onCameraSwitched](on-camera-switched.md) | [androidJvm]<br>suspend fun [onCameraSwitched](on-camera-switched.md)() |
| [onRecordingPause](on-recording-pause.md) | [androidJvm]<br>fun [onRecordingPause](on-recording-pause.md)(): [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? |
| [onRecordingPlay](on-recording-play.md) | [androidJvm]<br>fun [onRecordingPlay](on-recording-play.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |
| [onRecordingStop](on-recording-stop.md) | [androidJvm]<br>fun [onRecordingStop](on-recording-stop.md)(): [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? |
| [setVideoCaptureRecorder](set-video-capture-recorder.md) | [androidJvm]<br>fun [setVideoCaptureRecorder](set-video-capture-recorder.md)(videoCapture: [VideoCapture](https://developer.android.com/reference/kotlin/androidx/camera/video/VideoCapture.html)&lt;[Recorder](https://developer.android.com/reference/kotlin/androidx/camera/video/Recorder.html)&gt;): Job |
| [setVideoRecordEvent](set-video-record-event.md) | [androidJvm]<br>fun [setVideoRecordEvent](set-video-record-event.md)(value: [VideoRecordEvent](https://developer.android.com/reference/kotlin/androidx/camera/video/VideoRecordEvent.html)): Job |
