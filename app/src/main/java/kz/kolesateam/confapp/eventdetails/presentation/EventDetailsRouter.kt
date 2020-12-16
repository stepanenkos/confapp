package kz.kolesateam.confapp.eventdetails.presentation

import android.content.Context
import android.content.Intent

const val PUSH_NOTIFICATION_MESSAGE = "push_message"
const val EVENT_ID = "event_id"

class EventDetailsRouter {

    fun createIntent(
        context: Context
    ): Intent = Intent(context, EventDetailsActivity::class.java)

    fun createIntentForNotification(
        context: Context,
        messageFromPush: String,
    ): Intent = createIntent(context).apply {
        putExtra(PUSH_NOTIFICATION_MESSAGE, messageFromPush)
    }

    fun createIntentForEventDetails(
        context: Context,
        eventId: Int,
    ): Intent = createIntent(context).apply {
        putExtra(EVENT_ID, eventId)
    }
}