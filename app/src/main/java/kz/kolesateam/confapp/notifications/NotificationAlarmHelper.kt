package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.models.EventData
import java.util.*

const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
    private val application: Application,
) {
    private var pendingIntent: PendingIntent? = null
    private val alarmManager: AlarmManager? = application.getSystemService(
        Context.ALARM_SERVICE
    ) as? AlarmManager

    fun createNotificationAlarm(eventData: EventData) {
        pendingIntent =
            Intent(application, NotificationAlarmBroadcastReceiver::class.java).apply {
                putExtra(NOTIFICATION_CONTENT_KEY, eventData.title)
            }.let {
                PendingIntent.getBroadcast(application, eventData.id, it, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = eventData.startTime
        calendar.add(Calendar.MINUTE, -5)

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun cancelNotificationAlarm(eventData: EventData) {
        pendingIntent ?: return
        val intent = Intent(application, NotificationAlarmBroadcastReceiver::class.java)
        val pendingIn = PendingIntent.getBroadcast(application, eventData.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager?.cancel(pendingIn)
    }
}