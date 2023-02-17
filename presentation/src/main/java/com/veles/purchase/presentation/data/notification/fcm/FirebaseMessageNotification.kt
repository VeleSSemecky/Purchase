package com.veles.purchase.presentation.data.notification.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.veles.purchase.data.local.data.DataStore
import com.veles.purchase.domain.model.fcm.DataModel
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.presentation.activity.MainActivity
import javax.inject.Inject

class FirebaseMessageNotification @Inject constructor(
    private val context: Context,
    private val dataStore: DataStore,
    private val gson: Gson
) {

    operator fun invoke(dataNotification: Map<String, String>) {
        val dataModel = gson.fromJson(gson.toJson(dataNotification), DataModel::class.java)
        if (dataModel.tokenSender == dataStore.getFCMToken() || dataStore.isInForeground()) return
        val notificationBuilder = getBasicNotificationBuilder()
        notificationBuilder.setContentTitle(dataModel.title)
            .setContentText(dataModel.message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(createPendingIntent())
            .buildWithFirebaseNotification()
    }

    private fun NotificationCompat.Builder.buildWithFirebaseNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createBasicNotificationChannel(
            CHANNEL_ID_PURCHASE_NOTIFICATION,
            CHANNEL_ID_PURCHASE_NOTIFICATION,
            true
        )
        notificationManager.notify(
            UUID_PURCHASE_NOTIFICATION,
            ID_PURCHASE_NOTIFICATION,
            this.build()
        )
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getBasicNotificationBuilder(): NotificationCompat.Builder {
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(context, CHANNEL_ID_PURCHASE_NOTIFICATION)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(0)
        notificationBuilder.setSound(notificationSound)
        return notificationBuilder
    }

    private fun NotificationManager.createBasicNotificationChannel(
        channelId: String,
        channelName: String,
        playSound: Boolean
    ) {
        val channelImportance = if (playSound) {
            NotificationManager.IMPORTANCE_MAX
        } else {
            NotificationManager.IMPORTANCE_LOW
        }
        val channel = NotificationChannel(channelId, channelName, channelImportance)
        createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID_PURCHASE_NOTIFICATION = "CHANNEL_ID_PURCHASE_NOTIFICATION"
        private const val UUID_PURCHASE_NOTIFICATION = "UUID_PURCHASE_NOTIFICATION"
        private const val ID_PURCHASE_NOTIFICATION = 1
    }
}
