package com.example.foregroundservice19052023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var btnStartService: Button? = null
    private var btnStopService: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val messageFromService = intent.getStringExtra("open")
        if (messageFromService?.isNotEmpty() == true) {
            Toast.makeText(this, messageFromService, Toast.LENGTH_SHORT).show()
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