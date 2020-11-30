package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.Intent

class AllEventsRouter {
    fun createIntent(
        context: Context
    ) : Intent = Intent(context, AllEventsActivity::class.java)
}