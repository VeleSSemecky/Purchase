package com.veles.purchase.presentation.data.result

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class RequestUriContract : ActivityResultContract<Boolean, Uri?>() {

    private var uri: Uri? = null

    override fun createIntent(context: Context, input: Boolean): Intent {
        uri = try {
            context.createPhotoURI()
        } catch (e: Exception) {
            Uri.EMPTY
        }
        return context.getPickIntent(uri, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) return null
        return when {
            intent?.data is Uri -> intent.data
            uri != null -> uri
            else -> null
        }
    }

    @Throws(IOException::class)
    private fun Context.createPhotoURI(): Uri? {
        val photoURI: Uri?
        // Ensure that there's a camera activity to handle the intent
        val photoFile: File?
        // Create the File where the photo should go4
        photoFile = createImageFile(this)

        // Continue only if the File was successfully created
        photoURI = FileProvider.getUriForFile(
            this,
            packageName + FILE_PROVIDER,
            photoFile
        )

        grantUriPermission(
            packageName,
            photoURI,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        return photoURI
    }

    private fun Context.getPickIntent(photoURI: Uri?, isCameraPermission: Boolean): Intent {
        val intents: MutableList<Intent> = ArrayList()
        intents.add(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        if (photoURI != null && isCameraPermission) setCameraIntents(intents, photoURI)
        if (intents.isEmpty()) return Intent()
        val result = Intent.createChooser(intents.removeAt(0), null)
        if (intents.isNotEmpty()) {
            result.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
        }
        return result
    }

    private fun Context.setCameraIntents(cameraIntents: MutableList<Intent>, photoURI: Uri) {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager: PackageManager = packageManager
        packageManager.queryIntentActivities(captureIntent, 0).forEach { res ->
            val packageName = res.activityInfo.packageName
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(packageName)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            cameraIntents.add(intent)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        val imageFileName =
            SimpleDateFormat(YYYYMMDD_HHMMSS).format(Date())
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // Save a file: path for use with ACTION_VIEW intents
        return File(
            /* prefix */
            storageDir /* directory */,
            imageFileName + JPG
        )
    }

    companion object {
        const val FILE_PROVIDER = ".fileprovider"
        const val JPG = ".jpg"
        const val YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss"
    }
}
