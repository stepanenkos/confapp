package kz.kolesateam.confapp.events.data.models


data class EventData(
    val startTime: String,
    val endTime: String,
    val title: String,
    val place: String,
    val speaker: SpeakerData
)
