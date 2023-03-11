package com.veles.purchase.presentation.extensions

import android.app.Activity
import android.app.PictureInPictureParams
import com.veles.purchase.presentation.presentation.mvvm.pip.VideoRemoteAction

fun Activity.setPictureInPictureParams(
    params: PictureInPictureParams.Builder,
    vararg remoteActions: VideoRemoteAction
) = setPictureInPictureParams(
    params.setActions(remoteActions.map { it.action }).build()
)
