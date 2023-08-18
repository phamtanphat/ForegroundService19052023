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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {
    private val CHANNEL_ID = "my_channel"
    private var notification: Notification? = null
    private lateinit var notificationManager: NotificationManager
    private var isStart = false
    private var count = 0

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notification = createNotification(
            context = this@MyService,
            title = "Thong bao",
            message = "Notification da khoi tao",
            notificationManager = notificationManager
        )
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(AppConstant.ACTIVITY_START_SERVICE_KEY) ?: "") {
            AppConstant.START_COUNT_SERVICE -> {
                if (!isStart) {
                    Log.d("BBB", "Vao start")
                    loopCount()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun createNotification(
        context: Context,
        title: String,
        message: String,
        notificationManager: NotificationManager
    ): Notification {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        notification.setContentTitle(title)
        notification.setContentText(message)
        notification.setSmallIcon(R.drawable.ic_launcher_background)
        notification.priority = NotificationCompat.PRIORITY_DEFAULT

        val intent = Intent(this@MyService, MainActivity::class.java)
        intent.putExtra(AppConstant.NOTIFICATION_KEY, AppConstant.NOTIFICATION_OPEN)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification.setContentIntent(pendingIntent)

        // Action
        val intentStart = Intent(this@MyService, MainActivity::class.java)
        intentStart.putExtra(AppConstant.NOTIFICATION_KEY, AppConstant.NOTIFICATION_START)

        val pendingIntentStart = PendingIntent.getActivity(
            context,
            0,
            intentStart,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification.addAction(1, "Start", pendingIntentStart)

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

    private fun loopCount() {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(1000) {
                delay(1000)
                count++
                with(Dispatchers.Main) {
                    notification = createNotification(
                        context = this@MyService,
                        title = "Loop count",
                        message = "Count: $count",
                        notificationManager = notificationManager
                    )
                    notificationManager.notify(1, notification)
                }
            }
        }
    }
}