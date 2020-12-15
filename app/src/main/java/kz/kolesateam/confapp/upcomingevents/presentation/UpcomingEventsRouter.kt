package kz.kolesateam.confapp.upcomingevents.presentation

import android.content.Context
import android.content.Intent

class UpcomingEventsRouter {
    fun createIntent(
        context: Context
    ) : Intent = Intent(context, UpcomingEventsActivity::class.java)
}