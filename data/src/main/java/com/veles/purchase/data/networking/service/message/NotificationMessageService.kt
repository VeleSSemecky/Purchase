package com.veles.purchase.data.networking.service.message

import com.veles.purchase.data.networking.entity.fcm.NotificationMessageModelData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationMessageService {

    @POST("send")
    suspend fun sendNotificationMessage(
        @Body notificationMassage: NotificationMessageModelData
    ): Response<ResponseBody>
}
