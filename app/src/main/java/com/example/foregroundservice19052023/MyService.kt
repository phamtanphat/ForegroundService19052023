package com.example.foregroundservice19052023

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService: Service() {
    override fun onBind(p0: Intent?): IBinder? { return null }

    override fun onCreate() {
        super.onCreate()
        Log.d("BBB", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BBB", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BBB", "onDestroy")
    }
}