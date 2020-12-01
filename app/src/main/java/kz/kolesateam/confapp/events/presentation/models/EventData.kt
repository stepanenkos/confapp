package kz.kolesateam.confapp.events.presentation.models

import java.util.*


data class EventData(
    val startTime: Date,
    val endTime: Date,
    val title: String,
    val place: String,
    val speaker: SpeakerData
)
