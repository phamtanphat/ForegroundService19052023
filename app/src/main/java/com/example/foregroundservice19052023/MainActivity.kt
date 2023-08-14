package com.example.foregroundservice19052023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var btnStartService: Button? = null
    private var btnStopService: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartService = findViewById(R.id.button_start_service)
        btnStopService = findViewById(R.id.button_stop_service)

        btnStartService?.setOnClickListener {
            val service = Intent(this@MainActivity, MyService::class.java)
            startService(service)
        }
    }
}