package com.veles.purchase.presentation.data.notification.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.veles.purchase.domain.model.fcm.DataModel
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.common.Keys
import com.veles.purchase.presentation.presentation.activity.MainActivity

object NotificationBean {
    fun sendNotification(context: Context, dataNotification: DataModel) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context,
            Keys.NotificationData.INTENT_NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            Keys.NotificationData.INTENT_NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = dataNotification.title
        channel.setShowBadge(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        mNotificationManager.createNotificationChannel(channel)

        val defaultSoundUri = RingtoneManager
            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(
                context,
                Keys.NotificationData.INTENT_NOTIFICATION_CHANNEL_ID
            )
                .setColor(context.resources.getColor(R.color.color689f9b))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(dataNotification.title)
                .setContentText(dataNotification.message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        mNotificationManager.notify(
            Keys.NotificationData.INTENT_NOTIFICATION_NOTIFY_ID,
            notificationBuilder.build()
        )
    }
}
