package com.udacity.ui

import android.app.DownloadManager
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.udacity.*
import com.udacity.broadcast.Receiver
import com.udacity.buttonState.ButtonState
import com.udacity.buttonState.LoadingButton
import com.udacity.createNotificationChannel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var receiverIncome : Receiver
    lateinit var loading: LoadingButton

    var selectedId : Int =0

    private lateinit var glideRadioButton: RadioButton
    private lateinit var loadRadioButton: RadioButton
    private lateinit var retrofitRadioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loading = findViewById(R.id.loading_download_button)
        receiverIncome = Receiver(loading)

        IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE).also {
            registerReceiver(receiverIncome, it)
        }

        createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
            false,
            getString(R.string.app_name),
            "download notification channel"
        )

        glideRadioButton = findViewById(R.id.radio_glide)
        loadRadioButton = findViewById(R.id.radio_load)
        retrofitRadioButton = findViewById(R.id.radio_retrofit)

        if(glideRadioButton.isChecked){
            selectedId = R.id.radio_glide
        }else if (loadRadioButton.isChecked){
            selectedId = R.id.radio_load
        }else if (retrofitRadioButton.isChecked){
            selectedId = R.id.radio_retrofit
        }

        operationChecked(selectedId)

        loading.setOnClickListener {

            if (optionLink?.isNotEmpty() == true) {
                downloadFile()
            } else
                Toast.makeText(this, "No item selected , Please select one", Toast.LENGTH_SHORT).show()
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

//    companion object {
//        private const val URL =
//            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
//        private const val CHANNEL_ID = "channelId"
//    }
//
//    private val receiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//        }
//    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiverIncome)
    }

}
