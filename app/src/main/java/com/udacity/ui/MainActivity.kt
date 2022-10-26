package com.udacity.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.udacity.*
//import com.udacity.broadcast.RRReceiver
import com.udacity.buttonState.ButtonState
import com.udacity.buttonState.LoadingButton
import com.udacity.constant.Constant
import com.udacity.constant.Constant.downloadId
import com.udacity.createNotificationChannel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

//    private lateinit var RRReceiverIncome : RRReceiver
    lateinit var loading: LoadingButton

    var selectedId : Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loading = findViewById(R.id.loading_download_button)
//        RRReceiverIncome = RRReceiver(loading)

//        IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE).also {
//            registerReceiver(RRReceiverIncome, it)
//        }
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        createNotificationChannel(
            this
            //,
//            NotificationManagerCompat.IMPORTANCE_DEFAULT,

//            "Downloading File"
//            , "download notification channel"
        )

//        glideRadioButton = findViewById(R.id.radio_glide)
//        loadRadioButton = findViewById(R.id.radio_load)
//        retrofitRadioButton = findViewById(R.id.radio_retrofit)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.radio_glide) {
                operationChecked(R.id.radio_glide)
//                Toast.makeText(this, "true select", Toast.LENGTH_SHORT).show()
            } else if (checkedId == R.id.radio_load) {
                operationChecked(R.id.radio_load)
            } else if (checkedId == R.id.radio_retrofit) {
                operationChecked(R.id.radio_retrofit)
            }else{
                Toast.makeText(this, "Please select one", Toast.LENGTH_SHORT).show()
            }
        }


        loading.setOnClickListener {

            if (optionLink.isNotEmpty()) {
                downloadFile()
            } else Toast.makeText(this, "Please check one ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFile() {

            val request =
                DownloadManager.Request(Uri.parse(optionLink))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID = downloadManager.enqueue(request)
            loading.buttonState = ButtonState.Loading

    }

    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }
//
    private val receiver = object : BroadcastReceiver() {
    @SuppressLint("Range")
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

        loading.buttonState=ButtonState.Completed
        var downloadStatus = "Fail"
        val downloadManager =
            context!!.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query()
        query.setFilterById(id!!)

        val cursor = downloadManager.query(query)

        if (cursor.moveToFirst()) {

            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))


            if (status == DownloadManager.STATUS_FAILED){
                downloadStatus = "Fail"
                Constant.SELECTEDFILESTATUS =downloadStatus}
            else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                downloadStatus = "Success"
                Constant.SELECTEDFILESTATUS =downloadStatus
            }


        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNewNotification(
            context,"File download "+ Constant.SELECTEDFILENAME,
            "State of the file :"+ downloadStatus ,Constant.SELECTEDFILENAME, downloadStatus )

        }
    }
}

