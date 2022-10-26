package com.udacity

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.udacity.ui.DetailActivity

//private const val notificationID : Int = 0

/*** I have taken this function from session lead , there is repository github link of session lead
 * "https://github.com/mahmoudmagdi/NotifocationsApplication/blob/master/app/src/main/java/com/khlafawi/notifocationsapplication/NotificationsHelper.kt"
 * and i did changes on it
 * ***/


//new notification after downloading and show app name and details
fun NotificationManager.createNewNotification(
    context: Context, title: String, message: String,
    fileName: String, status: String
) {

    //TODO: STEP[3] Create a new notification
    val channelId = "channelId"
    val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {

        setSmallIcon(R.drawable.ic_cloud_download)
        setContentTitle(title)
        setContentText(message)
        setAutoCancel(true)
        priority = NotificationCompat.PRIORITY_DEFAULT

        val pendingIntent: PendingIntent?
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("fileName", fileName)
        intent.putExtra("status", status)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        }

        setContentIntent(pendingIntent)
    }

    val detailsActivity = Intent(
        context,
        DetailActivity::class.java
    )

    detailsActivity.action = context.getString(R.string.customAction)
    detailsActivity.putExtra("fileName", fileName)
    detailsActivity.putExtra("status", status)
    val detailActivityPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(context, 0, detailsActivity, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(
            context,
            0,
            detailsActivity,
            PendingIntent.FLAG_ONE_SHOT
        )
    }

    notificationBuilder.addAction(
        R.drawable.ic_launcher_foreground,
        "Go to Downloaded",
        detailActivityPendingIntent
    )


    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(1542001, notificationBuilder.build())

}