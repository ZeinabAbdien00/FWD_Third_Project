package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private const val NOTIFICATION_ID = 0

fun createNotificationChannel(
    context: Context,
    importance: Int,
    showBadge: Boolean,
    name: String,
    description: String
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channelId = "${context.packageName}-$name"
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        channel.setShowBadge(showBadge)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

fun NotificationManager.createNewNotification(
    selectContext: Context,
    header: String,
    messageDescription: String,
    autoCancelNotification: Boolean,
    downloadedFileName: String,
    downloadedFileStatus: String
) {

    val notificationChannelId = "${selectContext.packageName}-${selectContext.getString(R.string.app_name)}"
    val notificationBuilder = NotificationCompat.Builder(selectContext, notificationChannelId).apply {

        setSmallIcon(R.drawable.ic_launcher_foreground)
        setContentTitle(header)
        setContentText(messageDescription)
        setAutoCancel(autoCancelNotification)

        priority = NotificationCompat.PRIORITY_DEFAULT

        val pendingIntent: PendingIntent?
        val contextIntent = Intent(selectContext, DetailActivity::class.java)
        contextIntent.putExtra("fileName", downloadedFileName)
        contextIntent.putExtra("status", downloadedFileStatus)
        pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(selectContext, 0, contextIntent, FLAG_MUTABLE)
            } else {
            PendingIntent.getActivity(selectContext, 0, contextIntent, FLAG_ONE_SHOT)
            }

        setContentIntent(pendingIntent)
    }

    val detailsActivity = Intent(selectContext, DetailActivity::class.java)

    detailsActivity.action = selectContext.getString(R.string.action)
    detailsActivity.putExtra("notification_id", 1542001)
    detailsActivity.putExtra("fileName", downloadedFileName)
    detailsActivity.putExtra("status", downloadedFileStatus)
    val detailActivityPendingIntent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(selectContext, 0, detailsActivity, FLAG_MUTABLE)
        } else {
        PendingIntent.getActivity(selectContext, 0, detailsActivity, FLAG_ONE_SHOT)
        }

    notificationBuilder.addAction(
        R.drawable.ic_launcher_foreground,
        "Go to activity",
        detailActivityPendingIntent
    )


    val notificationManager = NotificationManagerCompat.from(selectContext)
    notificationManager.notify(1001, notificationBuilder.build())

}

fun NotificationManager.sendNotification2( context: Context, fileName: String, status: String) {

    val contentIntent = Intent(context, DetailActivity::class.java)
    contentIntent.apply {
        putExtra("fileName", fileName)
        putExtra("status", status)
    }

    val contentPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(
            context,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}


