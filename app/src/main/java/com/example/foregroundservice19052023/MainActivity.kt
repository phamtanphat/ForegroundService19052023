package com.example.foregroundservice19052023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var btnStartService: Button? = null
    private var btnStopService: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val messageFromService = intent.getStringExtra(AppConstant.NOTIFICATION_KEY)
        if (messageFromService?.isNotEmpty() == true) {
            when (messageFromService) {
                AppConstant.NOTIFICATION_OPEN -> {
                    Toast.makeText(this, "Open from service", Toast.LENGTH_SHORT).show()
                }
                AppConstant.NOTIFICATION_START -> {
                    val intent = Intent(this@MainActivity, MyService::class.java)
                    intent.putExtra(AppConstant.ACTIVITY_START_SERVICE_KEY, AppConstant.START_COUNT_SERVICE)
                    startService(intent)
                }
            }
        }

        btnStartService = findViewById(R.id.button_start_service)
        btnStopService = findViewById(R.id.button_stop_service)

        btnStartService?.setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            startService(intent)
        }

        btnStopService?.setOnClickListener {
            val service = Intent(this@MainActivity, MyService::class.java)
            stopService(service)
        }
    }
}