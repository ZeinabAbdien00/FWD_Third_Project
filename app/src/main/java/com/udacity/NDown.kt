package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build

//creating new notification channel to show details of downloading link
fun createNotificationChannel(
    context: Context
//    , importance: Int,
//    name: String
//    ,description: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


        val channelId = "Downloaded"
        val channel=NotificationChannel(channelId,"channel_name", NotificationManager.IMPORTANCE_DEFAULT)
        channel.setShowBadge(false)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

}


