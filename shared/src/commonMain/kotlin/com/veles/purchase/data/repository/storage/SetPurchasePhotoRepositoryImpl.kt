package com.veles.purchase.data.repository.storage

import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.storage.detectImageMimeType
import com.veles.purchase.data.util.sha1Hex
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.SetPurchasePhotoRepository
import com.veles.purchase.platform.logger.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class CloudinaryUploadResponse(
    val secure_url: String? = null,
    val error: CloudinaryError? = null
)

@Serializable
private data class CloudinaryError(val message: String = "Unknown error")

private val responseJson = Json { ignoreUnknownKeys = true }

class SetPurchasePhotoRepositoryImpl(
    private val httpClient: HttpClient
) : SetPurchasePhotoRepository {

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun setPurchasePhotos(list: List<PurchasePhotoModel>): List<PurchasePhotoModel> {
        return list.map { photo ->
            if (photo.status == PhotoStatus.LOCAL && photo.bytes != null) {
                val publicId = storagePath(photo)
                val timestamp = kotlin.time.Clock.System.now().toEpochMilliseconds() / 1000
                // Params sorted alphabetically (public_id < timestamp), API_SECRET appended (no &)
                val signatureInput = "public_id=$publicId&timestamp=$timestamp${EnvironmentConfig.CLOUDINARY_API_SECRET}"
                val signature = sha1Hex(signatureInput)

                AppLogger.d(TAG, "Uploading to cloud=${EnvironmentConfig.CLOUDINARY_CLOUD_NAME}")
                AppLogger.d(TAG, "apiKey=${EnvironmentConfig.CLOUDINARY_API_KEY} ts=$timestamp sig=$signature")

                val mimeType = photo.bytes.detectImageMimeType()
                val base64File = "data:$mimeType;base64,${Base64.encode(photo.bytes)}"

                val responseText = httpClient.submitForm(
                    url = uploadUrl(),
                    formParameters = parameters {
                        append("file", base64File)
                        append("public_id", publicId)
                        append("api_key", EnvironmentConfig.CLOUDINARY_API_KEY)
                        append("timestamp", timestamp.toString())
                        append("signature", signature)
                    }
                ).bodyAsText()

                AppLogger.d(TAG, "Cloudinary response: $responseText")

                val parsed = responseJson.decodeFromString<CloudinaryUploadResponse>(responseText)
                val secureUrl = parsed.secure_url
                    ?: throw IllegalStateException(
                        "Cloudinary upload failed: ${parsed.error?.message ?: responseText}"
                    )

                photo.copy(
                    purchasePhotoUri = secureUrl,
                    status = PhotoStatus.DOWNLOADED,
                    bytes = null
                )
            } else {
                photo
            }
        }
    }

    companion object {
        private const val TAG = "CloudinaryUpload"

        fun storagePath(photo: PurchasePhotoModel) =
            "purchases/${photo.purchaseId}/${photo.purchasePhotoId}"

        private fun uploadUrl() =
            "https://api.cloudinary.com/v1_1/${EnvironmentConfig.CLOUDINARY_CLOUD_NAME}/image/upload"
    }
}
