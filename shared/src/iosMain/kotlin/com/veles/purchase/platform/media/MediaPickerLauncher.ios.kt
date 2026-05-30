package com.veles.purchase.platform.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.UniformTypeIdentifiers.UTTypeImage
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberMediaPickerLauncher(onResult: (ByteArray) -> Unit): () -> Unit {
    val onResultRef = remember { onResult }

    return remember {
        {
            val config = PHPickerConfiguration().apply {
                filter = PHPickerFilter.imagesFilter
                selectionLimit = 5
            }
            val picker = PHPickerViewController(configuration = config)
            picker.delegate = object : NSObject(), PHPickerViewControllerDelegateProtocol {
                @Suppress("UNCHECKED_CAST")
                override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                    picker.dismissViewControllerAnimated(true, null)
                    val results = didFinishPicking as List<PHPickerResult>
                    results.forEach { result ->
                        result.itemProvider.loadDataRepresentationForTypeIdentifier(
                            UTTypeImage.identifier
                        ) { data, _ ->
                            val nsData = data ?: return@loadDataRepresentationForTypeIdentifier
                            val length = nsData.length.toInt()
                            if (length > 0) {
                                val bytes = nsData.bytes?.readBytes(length)
                                    ?: return@loadDataRepresentationForTypeIdentifier
                                onResultRef(bytes)
                            }
                        }
                    }
                }
            }

            UIApplication.sharedApplication.keyWindow?.rootViewController
                ?.presentViewController(picker, animated = true, completion = null)
        }
    }
}

