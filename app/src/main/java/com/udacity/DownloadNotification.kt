package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

//creating new notification channel to show details of downloading link
fun createNotificationChannel(
    context: Context,
    importance: Int,
    name: String,
    description: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channelId = "Zeinab"
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}


