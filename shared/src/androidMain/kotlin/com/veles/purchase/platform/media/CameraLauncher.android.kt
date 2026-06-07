package com.veles.purchase.platform.media

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
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

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scanResult = com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            val pages = scanResult?.pages
            if (!pages.isNullOrEmpty()) {
                val uri = pages[0].imageUri
                context.contentResolver.openInputStream(uri)?.use { input ->
                    onResult(compressImageBytes(input.readBytes()))
                }
            }
        }
    }

    val scanner = remember {
        val options = GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(true)
            .setPageLimit(1)
            .setResultFormats(RESULT_FORMAT_JPEG)
            .setScannerMode(SCANNER_MODE_FULL)
            .build()
        GmsDocumentScanning.getClient(options)
    }

    return {
        scanner.getStartScanIntent(context as ComponentActivity)
            .addOnSuccessListener { intentSender ->
                scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
            }
            .addOnFailureListener { e ->
                android.util.Log.e("CameraLauncher", "Failed to start document scanner", e)
            }
    }
}
