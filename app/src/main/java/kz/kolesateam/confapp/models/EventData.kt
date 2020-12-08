package kz.kolesateam.confapp.models

import java.util.*

data class EventData(
    val startTime: Date,
    val endTime: Date,
    val title: String,
    val place: String,
    val speaker: SpeakerData,
    var isFavorite: Boolean = false
)
