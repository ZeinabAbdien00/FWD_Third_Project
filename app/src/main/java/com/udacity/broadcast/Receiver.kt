package com.udacity.broadcast

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.udacity.constant.Constant
import com.udacity.buttonState.ButtonState
import com.udacity.buttonState.LoadingButton
import com.udacity.createNewNotification

class Receiver (loadingButton: LoadingButton): BroadcastReceiver() {
    private val stateLoadingButton = loadingButton
    @SuppressLint("Range")
    override fun onReceive(contextSelected: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: return

        stateLoadingButton.buttonState = ButtonState.Completed

        val downloadManager = contextSelected!!.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

        val query = DownloadManager.Query()
        query.setFilterById(id!!)

        val cursor = downloadManager.query(query)

        if (cursor.moveToFirst()) {

            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

            var downloadStatus = "Fail"
            if (DownloadManager.STATUS_SUCCESSFUL == status)
                downloadStatus = "Success"

            Constant.SELECTEDFILESTATUS =downloadStatus

            val notificationManager = contextSelected!!.getSystemService(NotificationManager::class.java)
            notificationManager.createNewNotification(
                contextSelected,"Downloading "+ Constant.SELECTEDFILENAME,
                "Download State : " + downloadStatus,
                true, Constant.SELECTEDFILENAME,
                downloadStatus
            )
        }

    }
}
