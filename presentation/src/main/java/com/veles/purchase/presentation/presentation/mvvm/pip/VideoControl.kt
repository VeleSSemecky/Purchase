package com.veles.purchase.presentation.presentation.mvvm.pip

import android.app.PendingIntent
import android.app.RemoteAction
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.common.Keys
import java.util.UUID

enum class VideoControl {
    PAUSE,
    STOP,
    PLAY,
    SWITCH
}

sealed interface VideoRemoteAction {

    val action: RemoteAction

    fun onRemoteAction(
        context: Context,
        @DrawableRes resId: Int,
        videoControl: VideoControl
    ) = RemoteAction(
        Icon.createWithResource(context, resId),
        videoControl.name,
        videoControl.name,
        PendingIntent.getBroadcast(
            context,
            UUID.randomUUID().hashCode(),
            Intent("G_ALARM_ACTION").apply {
                putExtra(Keys.Video.REMOTE_ACTION, videoControl.name)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    )

    operator fun invoke() = action
}

class PauseRemoteAction(context: Context) : VideoRemoteAction {
    override val action = onRemoteAction(
        context,
        R.drawable.ic_baseline_pause_24,
        VideoControl.PAUSE
    )
}

class StopRemoteAction(context: Context) : VideoRemoteAction {
    override val action = onRemoteAction(
        context,
        R.drawable.ic_baseline_stop_24,
        VideoControl.STOP
    )
}

class PlayRemoteAction(context: Context) : VideoRemoteAction {
    override val action = onRemoteAction(
        context,
        R.drawable.ic_baseline_play_arrow_24,
        VideoControl.PLAY
    )
}

class SwitchRemoteAction(context: Context) : VideoRemoteAction {
    override val action = onRemoteAction(
        context,
        R.drawable.ic_baseline_cameraswitch_24,
        VideoControl.SWITCH
    )
}
