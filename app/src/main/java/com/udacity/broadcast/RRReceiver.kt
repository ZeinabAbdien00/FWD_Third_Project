package com.udacity.broadcast
//
//import android.annotation.SuppressLint
//import android.app.DownloadManager
//import android.app.NotificationManager
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.database.Cursor
//import android.provider.Settings.Global.getString
//import androidx.appcompat.app.AppCompatActivity
//import com.udacity.R
//import com.udacity.constant.Constant
//import com.udacity.buttonState.ButtonState
//import com.udacity.buttonState.LoadingButton
//import com.udacity.createNewNotification
//
//class RRReceiver (loadingButton: LoadingButton): BroadcastReceiver() {
//    private val stateLoadingButton = loadingButton
//    private var downloadStatus = "Fail"
//    @SuppressLint("Range")
//    override fun onReceive(contextSelected: Context?, p1: Intent?) {
//        val downloadId = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//
//        stateLoadingButton.buttonState = ButtonState.Completed
//
//        val downloadManager =
//            contextSelected!!.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
//        val query = DownloadManager.Query()
//        query.setFilterById(downloadId!!)
//
//        val cursor = downloadManager.query(query)
//
//        if (cursor.moveToFirst()) {
//
//            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//
//
//            if (status == DownloadManager.STATUS_FAILED){
//                downloadStatus = "Fail"
//                Constant.SELECTEDFILESTATUS =downloadStatus}
//            else if (status == DownloadManager.STATUS_SUCCESSFUL) {
//                downloadStatus = "Success"
//                Constant.SELECTEDFILESTATUS =downloadStatus
//            }
//
//
//        }
//
//        val notificationManager = contextSelected.getSystemService(NotificationManager::class.java)
//        notificationManager.createNewNotification(
//            contextSelected,"Downloaded "+ Constant.SELECTEDFILENAME,
//            "State : $downloadStatus"
////            ,
////            Constant.SELECTEDFILENAME
//        )
//
//
//    }
//}
