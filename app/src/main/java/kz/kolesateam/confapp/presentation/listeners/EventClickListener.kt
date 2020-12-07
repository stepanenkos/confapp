package kz.kolesateam.confapp.presentation.listeners

import kz.kolesateam.confapp.models.EventData

interface EventClickListener {
    fun onEventClick(
        eventData: EventData,
    )
}