package com.veles.purchase.data.repository.storage

import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.util.sha1Hex
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters

class DeletePurchasePhotoRepositoryImpl(
    private val httpClient: HttpClient
) : DeletePurchasePhotoRepository {

    override suspend fun deletePhoto(purchasePhotoModel: PurchasePhotoModel) {
        if (purchasePhotoModel.status == PhotoStatus.DOWNLOADED) {
            val publicId = SetPurchasePhotoRepositoryImpl.storagePath(purchasePhotoModel)
            val timestamp = kotlin.time.Clock.System.now().toEpochMilliseconds() / 1000
            val signature = sha1Hex(
                "public_id=$publicId&timestamp=$timestamp${EnvironmentConfig.CLOUDINARY_API_SECRET}"
            )

            httpClient.submitForm(
                url = destroyUrl(),
                formParameters = parameters {
                    append("public_id", publicId)
                    append("api_key", EnvironmentConfig.CLOUDINARY_API_KEY)
                    append("timestamp", timestamp.toString())
                    append("signature", signature)
                }
            )
        }
        // LOCAL photos (not yet uploaded) are simply ignored
    }

    override suspend fun deletePhotos(purchaseId: String, listImage: List<PurchasePhotoModel>) {
        listImage.forEach { deletePhoto(it) }
    }

    private fun destroyUrl() =
        "https://api.cloudinary.com/v1_1/${EnvironmentConfig.CLOUDINARY_CLOUD_NAME}/image/destroy"
}
