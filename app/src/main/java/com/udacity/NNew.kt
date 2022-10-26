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

private const val notificationID : Int = 0

//new notification after downloading and show app name and details
@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.createNewNotification(
    selectContext: Context,
    downloadedFileName: String,
    downloadedFileStatus: String
) {

    val contextIntent = Intent(selectContext, DetailActivity::class.java)
    contextIntent.putExtra("fileName", downloadedFileName)
    contextIntent.putExtra("status", downloadedFileStatus)


//    val pendingIntent =PendingIntent.getActivity(
//        selectContext, notificationID,
//        contextIntent, FLAG_UPDATE_CURRENT
//    )

    val notificationBuilder = NotificationCompat.Builder(selectContext, "Downloading").apply {

        setContentTitle("Udacity: Android Kotlin Nanodegree")
        setContentText("In Download")
        setAutoCancel(true)
        priority = NotificationCompat.PRIORITY_DEFAULT
        val pendingIntent =PendingIntent.getActivity(
            selectContext, notificationID,
            contextIntent, FLAG_UPDATE_CURRENT
        )
        addAction(R.drawable.ic_launcher_foreground, "Show", pendingIntent)
    }

    notify(notificationID, notificationBuilder.build())


//    val detailsActivity = Intent(selectContext, DetailActivity::class.java)
//
//    detailsActivity.action = selectContext.getString(R.string.action)
//    detailsActivity.putExtra("notification_id", 1542001)
//    detailsActivity.putExtra("fileName", downloadedFileName)
//    detailsActivity.putExtra("status", downloadedFileStatus)
//    val detailActivityPendingIntent =
//        when (Build.VERSION.SDK_INT){
//            Build.VERSION_CODES.S -> PendingIntent.getActivity(selectContext, 0, detailsActivity, PendingIntent.FLAG_MUTABLE)
//            else -> PendingIntent.getActivity(selectContext, 0, detailsActivity, FLAG_ONE_SHOT)
//        }

}