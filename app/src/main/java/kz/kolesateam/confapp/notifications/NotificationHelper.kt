package kz.kolesateam.confapp.notifications

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.eventdetails.presentation.EventDetailsRouter

object NotificationHelper {
    private const val CHANNEL_ID = "favorite_notification_channel"

    private lateinit var application: Application
    private var notificationIdCounter: Int = 0

    fun init(application: Application) {
        this.application = application
        initChannel()
    }

    fun sendNotification(
        title: String,
        content: String,
    ) {
        val notification: Notification = getNotification(
            title = title,
            content = content
        )

        NotificationManagerCompat.from(application).notify(
            notificationIdCounter++,
            notification
        )
    }

    private fun getNotification(
        title: String,
        content: String,
    ): Notification = NotificationCompat.Builder(
        application, CHANNEL_ID
    )
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(R.drawable.ic_notification)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(getPendingIntent(content))
        .setAutoCancel(true)
        .build()

    private fun getPendingIntent(
        content: String,
    ): PendingIntent {
        val eventDetailsEvent = EventDetailsRouter().createIntentForNotification(
            context = application,
            messageFromPush = content,
        ).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        return PendingIntent.getActivity(application,
            0,
            eventDetailsEvent,
            PendingIntent.FLAG_ONE_SHOT)

    }

    private fun initChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val importance: Int = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            importance
        )

        val notificationManager: NotificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}