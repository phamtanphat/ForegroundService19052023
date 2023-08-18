package com.example.foregroundservice19052023

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private val CHANNEL_ID = "my_channel"
    private var notification: Notification? = null
    private lateinit var notificationManager: NotificationManager

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notification = createNotification(this@MyService, notificationManager)
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun createNotification(
        context: Context,
        notificationManager: NotificationManager
    ): Notification {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        notification.setContentTitle("Thong bao")
        notification.setContentText("App co phien ban moi")
        notification.setSmallIcon(R.drawable.ic_launcher_background)
        notification.priority = NotificationCompat.PRIORITY_DEFAULT

        val intent = Intent(this@MyService, MainActivity::class.java)
        intent.putExtra("open", "Open from service")

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        notification.setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Version new",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        return notification.build()
    }
}