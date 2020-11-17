package kz.kolesateam.confapp.events.data.models

object FormattingString {
    private const val NUMBER_OF_SPACES_FOR_EVENTS = 4
    private const val NUMBER_OF_SPACES_FOR_SPEAKER = 12
    fun formatString(list: List<BranchApiData>): String {
        var stringBuilder = StringBuilder()
        for (elem in list) {
            stringBuilder.append("\nid = ${elem.id},\n")
                .append("title = ${elem.title},\n")
            if (elem.events != null) {
                for (events in elem.events) {
                    val id = "id = ${events.id},\n"
                    val startTime = "startTime = ${events.startTime},\n"
                    val endTime = "endTime = ${events.endTime},\n"
                    val title = "title = ${events.title},\n"
                    val description = "description = ${events.description},\n"
                    val place = "place = ${events.place}\n"
                    stringBuilder
                        .append("events {\n")
                        .append(id
                            .padStart(id.length + NUMBER_OF_SPACES_FOR_EVENTS))
                        .append(startTime
                            .padStart(startTime.length + NUMBER_OF_SPACES_FOR_EVENTS))
                        .append(endTime
                            .padStart(endTime.length + NUMBER_OF_SPACES_FOR_EVENTS))
                        .append(title
                            .padStart(title.length + NUMBER_OF_SPACES_FOR_EVENTS))
                        .append(description
                            .padStart(description.length + NUMBER_OF_SPACES_FOR_EVENTS))
                        .append(place
                            .padStart(place.length + NUMBER_OF_SPACES_FOR_EVENTS))
                    if (events.speaker != null) {
                        val speakerId = "id = ${events.speaker.id},\n"
                        val fullName = "fullName = ${events.speaker.fullName},\n"
                        val job = "job = ${events.speaker.job},\n"
                        val photoUrl = "photoUrl = ${events.speaker.photoUrl}\n    }\n}\n"
                        stringBuilder
                            .append("speaker { \n")
                            .append(speakerId
                                .padStart(speakerId.length + NUMBER_OF_SPACES_FOR_SPEAKER)
                            )
                            .append(fullName
                                .padStart(fullName.length+ NUMBER_OF_SPACES_FOR_SPEAKER))
                            .append(job
                                .padStart(job.length + NUMBER_OF_SPACES_FOR_SPEAKER))
                            .append(photoUrl
                                .padStart(photoUrl.length + NUMBER_OF_SPACES_FOR_SPEAKER))
                    }
                }
            }
        }

        return stringBuilder.toString()
    }
}