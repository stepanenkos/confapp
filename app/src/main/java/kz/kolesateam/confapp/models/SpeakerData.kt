package kz.kolesateam.confapp.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SpeakerData(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("fullName")
    val fullName: String,
    @JsonProperty("job")
    val job: String,
    @JsonProperty("photoUrl")
    val photoUrl: String,
    @JsonProperty("isInvited")
    var isInvited: Boolean = false
)
