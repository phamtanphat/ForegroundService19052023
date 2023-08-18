package com.example.foregroundservice19052023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var btnStartService: Button? = null
    private var btnStopService: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("BBB", "onCreate")

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

    override fun onStart() {
        super.onStart()
        Log.d("BBB", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("BBB", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("BBB", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("BBB", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BBB", "onDestroy")
    }
}