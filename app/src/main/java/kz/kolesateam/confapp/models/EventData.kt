package kz.kolesateam.confapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventData(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("startTime")
    val startTime: Date,
    @JsonProperty("endTime")
    val endTime: Date,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("place")
    val place: String,
    @JsonProperty("speaker")
    val speaker: SpeakerData,
    @JsonProperty("isFavorite")
    var isFavorite: Boolean = false
)
