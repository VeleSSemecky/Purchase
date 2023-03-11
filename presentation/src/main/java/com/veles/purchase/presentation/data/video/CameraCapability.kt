package com.veles.purchase.presentation.data.video

import androidx.camera.video.Quality

data class CameraCapability(
    var lensFacing: Int,
    var selector: List<Quality>
)
