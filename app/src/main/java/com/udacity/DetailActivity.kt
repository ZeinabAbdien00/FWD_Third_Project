package com.udacity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var fileIncomeState : TextView
    private lateinit var fileIncomeName : TextView
    private lateinit var okButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        fileIncomeName = findViewById(R.id.file_name)
        fileIncomeState = findViewById(R.id.file_state)
        okButton = findViewById(R.id.ok_button)

        val fileName = intent.getStringExtra("fileName")
        val fileState = intent.getStringExtra("status")

        fileIncomeName.text = fileName.toString()
        fileIncomeState.text = fileState.toString()

        okButton.setOnClickListener{

            val backIntent = Intent(this , MainActivity::class.java )
            startActivity(backIntent)
        }

    }

}
