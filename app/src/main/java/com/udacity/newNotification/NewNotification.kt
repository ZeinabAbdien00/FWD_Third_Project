package com.udacity.newNotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.udacity.R
import com.udacity.ui.DetailActivity

//new notification after downloading and show app name and details
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
                PendingIntent.getActivity(selectContext, 0, contextIntent,
                    PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getActivity(selectContext, 0, contextIntent,
                    PendingIntent.FLAG_ONE_SHOT)
            }

        setContentIntent(pendingIntent)
    }

    val detailsActivity = Intent(selectContext, DetailActivity::class.java)

    detailsActivity.action = selectContext.getString(R.string.action)
    detailsActivity.putExtra("notification_id", 1542001)
    detailsActivity.putExtra("fileName", downloadedFileName)
    detailsActivity.putExtra("status", downloadedFileStatus)
    val detailActivityPendingIntent =
        when (Build.VERSION.SDK_INT){
            Build.VERSION_CODES.S -> PendingIntent.getActivity(selectContext, 0, detailsActivity, PendingIntent.FLAG_MUTABLE)
            else -> PendingIntent.getActivity(selectContext, 0, detailsActivity, FLAG_ONE_SHOT)
        }

//when user click on notification it do to detail activity
    notificationBuilder.addAction(
        R.drawable.ic_launcher_foreground,
        "Go to activity",
        detailActivityPendingIntent
    )


    val notificationManager = NotificationManagerCompat.from(selectContext)
    notificationManager.notify(1504, notificationBuilder.build())

}