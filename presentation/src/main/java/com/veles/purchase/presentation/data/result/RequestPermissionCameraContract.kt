package com.veles.purchase.presentation.data.result

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat

class RequestPermissionCameraContract : ActivityResultContract<Unit, Boolean>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(ACTION_REQUEST_PERMISSIONS).putExtra(
            EXTRA_PERMISSIONS,
            arrayOf(Manifest.permission.CAMERA)
        )
    }

    @Suppress("AutoBoxing")
    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (intent == null || resultCode != Activity.RESULT_OK) return false
        val grantResults =
            intent.getIntArrayExtra(
                EXTRA_PERMISSION_GRANT_RESULTS
            )
        return grantResults?.any { result ->
            result == PackageManager.PERMISSION_GRANTED
        } == true
    }

    override fun getSynchronousResult(
        context: Context,
        input: Unit
    ): SynchronousResult<Boolean>? {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        return if (granted) {
            SynchronousResult(true)
        } else {
            null
        }
    }

    companion object {

        const val ACTION_REQUEST_PERMISSIONS =
            "androidx.activity.result.contract.action.REQUEST_PERMISSIONS"

        const val EXTRA_PERMISSIONS = "androidx.activity.result.contract.extra.PERMISSIONS"

        const val EXTRA_PERMISSION_GRANT_RESULTS =
            "androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS"
    }
}
