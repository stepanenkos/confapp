package kz.kolesateam.confapp.events.data.models

class SpeakerApiDataMapper: Mapper<SpeakerApiData, SpeakerData> {
    private val DEFAULT_SPEAKER_NAME = "Имя Спикера не указано"
    private val DEFAULT_JOB_SPEAKER = "Работа Спикера не указана"
    override fun map(data: SpeakerApiData?): SpeakerData {
        return SpeakerData(
            fullName = data?.fullName ?: DEFAULT_SPEAKER_NAME,
            job = data?.job ?: DEFAULT_JOB_SPEAKER
        )
    }
}