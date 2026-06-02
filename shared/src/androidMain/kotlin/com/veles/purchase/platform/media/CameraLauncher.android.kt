package com.veles.purchase.platform.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

private const val MAX_DIMENSION = 1200
private const val JPEG_QUALITY = 75

internal fun compressImageBytes(bytes: ByteArray): ByteArray {
    val original = BitmapFactory.decodeByteArray(bytes, 0, bytes.size) ?: return bytes
    val w = original.width
    val h = original.height
    if (w <= MAX_DIMENSION && h <= MAX_DIMENSION) {
        val out = ByteArrayOutputStream()
        original.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
        original.recycle()
        return out.toByteArray()
    }
    val scale = MAX_DIMENSION.toFloat() / maxOf(w, h)
    val scaled = Bitmap.createScaledBitmap(original, (w * scale).toInt(), (h * scale).toInt(), true)
    original.recycle()
    val out = ByteArrayOutputStream()
    scaled.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
    scaled.recycle()
    return out.toByteArray()
}

@Composable
actual fun rememberCameraLauncher(onResult: (ByteArray) -> Unit): () -> Unit {
    val context = LocalContext.current
    var photoFile by remember { mutableStateOf<File?>(null) }

    val takePicture = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoFile?.readBytes()?.let { onResult(compressImageBytes(it)) }
        }
        photoFile?.delete()
        photoFile = null
    }

    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val file = File(context.cacheDir, "camera_photo_${System.nanoTime()}.jpg")
            photoFile = file
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            takePicture.launch(uri)
        }
    }

    return {
        requestPermission.launch(android.Manifest.permission.CAMERA)
    }
}
