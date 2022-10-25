package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.ui.DetailActivity

private val notificationID : Int = 0

//new notification after downloading and show app name and details
fun NotificationManager.createNewNotification(
    selectContext: Context,
    header: String,
    messageDescription: String,
//    autoCancelNotification: Boolean,
    downloadedFileName: String,
    downloadedFileStatus: String
) {

    val contextIntent = Intent(selectContext, DetailActivity::class.java)
    contextIntent.putExtra("fileName", downloadedFileName)
    contextIntent.putExtra("status", downloadedFileStatus)


    val pendingIntent =PendingIntent.getActivity(
        selectContext, notificationID,
        contextIntent, FLAG_UPDATE_CURRENT
    )

    val notificationChannelId = "${selectContext.packageName}-${selectContext.getString(R.string.app_name)}"
    val notificationBuilder = NotificationCompat.Builder(selectContext, notificationChannelId).apply {

        setContentTitle(header)
        setContentText(messageDescription)
        //when user click on notification it do to detail activity
        addAction(
            R.drawable.ic_launcher_foreground,
            "Show",
            pendingIntent
        )

    }
    notify(notificationID, notificationBuilder.build())


//    val detailsActivity = Intent(selectContext, DetailActivity::class.java)
//
//    detailsActivity.action = selectContext.getString(R.string.action)
////    detailsActivity.putExtra("notification_id", 1542001)
//    detailsActivity.putExtra("fileName", downloadedFileName)
//    detailsActivity.putExtra("status", downloadedFileStatus)
//    val detailActivityPendingIntent =
//        when (Build.VERSION.SDK_INT){
//            Build.VERSION_CODES.S -> PendingIntent.getActivity(selectContext, 0, detailsActivity, PendingIntent.FLAG_MUTABLE)
//            else -> PendingIntent.getActivity(selectContext, 0, detailsActivity, FLAG_ONE_SHOT)
//        }



}