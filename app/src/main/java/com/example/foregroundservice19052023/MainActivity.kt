package com.example.foregroundservice19052023

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private var btnStartService: Button? = null
    private var btnStopService: Button? = null
    private var tvCount: TextView? = null
    private lateinit var myService: MyService

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
        tvCount = findViewById(R.id.text_view_count)

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
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        Intent(this, MyService::class.java).also { intent ->
            unbindService(connection)
        }
    }

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            myService = (service as MyService.MyBinder).getService()
            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    tvCount?.text = "Count: ${myService.getCount()}"
                    Handler(Looper.getMainLooper()).postDelayed(this, 1000)
                }
            }, 1000)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {

        }
    }
}